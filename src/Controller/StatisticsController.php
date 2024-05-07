<?php

namespace App\Controller;
use App\Entity\ReservationMusee;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;
use DateTime;
class StatisticsController extends AbstractController
{
 
    #[Route('/statistics', name: 'app_statistics')]
    public function index(EntityManagerInterface $entityManager): Response
    {
        // Récupérer l'année en cours
        $currentYear = (int) date('Y');

        // Calculer l'année précédente
        $previousYear = $currentYear - 1;

        // Récupérer le nombre de réservations pour cette année
        $reservationsThisYear = $this->getReservationsByYear($entityManager, $currentYear);

        // Récupérer le nombre de réservations pour l'année précédente
        $reservationsLastYear = $this->getReservationsByYear($entityManager, $previousYear);

        // Afficher les statistiques
        return $this->render('statistics/index.html.twig', [
            'reservations_this_year' => $reservationsThisYear,
            'reservations_last_year' => $reservationsLastYear,
        ]);
    }

    // Fonction pour récupérer le nombre de réservations par année
    private function getReservationsByYear(EntityManagerInterface $entityManager, int $year): int
    {
        // Créer un objet DateTime pour le début de l'année spécifiée
        $startOfYear = new DateTime("$year-01-01");

        // Créer un objet DateTime pour la fin de l'année spécifiée
        $endOfYear = new DateTime("$year-12-31 23:59:59");

        // Récupérer le nombre de réservations pour l'année spécifiée
        $reservations = $entityManager->getRepository(ReservationMusee::class)
            ->createQueryBuilder('r')
            ->select('COUNT(r.reservationId)')
            ->andWhere('r.dateReservation BETWEEN :start AND :end')
            ->setParameter('start', $startOfYear)
            ->setParameter('end', $endOfYear)
            ->getQuery()
            ->getSingleScalarResult();

        return (int) $reservations;
    }
}
