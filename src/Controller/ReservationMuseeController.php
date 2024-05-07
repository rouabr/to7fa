<?php

namespace App\Controller;

use App\Entity\ReservationMusee;
use App\Entity\Musee;
use App\Entity\User;
use App\Form\ReservationMuseeType;
use App\Form\Reservationmusee1Type;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use DateTimeImmutable;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Mailer\Transport;
use Symfony\Component\Mailer\Transport\Dsn;


#[Route('/reservation/musee')]
class ReservationMuseeController extends AbstractController
{
   

    #[Route('/validate-email', name: 'validate_email')]
    public function validateEmail(Request $request, MailerInterface $mailer): Response
    {
        $message = (new Email())
            ->from('yasmine.laajailia@esprit.tn')
            ->to('kassem.benhenda@esprit.tn')
            ->subject('Hello')
            ->text('rod belek tadhrbek karhba');

            $mailer->send($message);

            return new Response('Message sent.');


            dump('code23');
    
    }
    #[Route('/', name: 'app_reservation_musee_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $reservationMusees = $entityManager
            ->getRepository(ReservationMusee::class)
            ->findAll();
    
        // Créer un tableau vide pour stocker les détails des musées
        $museeDetails = [];
        $choices = [];
        $musees = $entityManager->getRepository(Musee::class)->findAll();
        foreach ($musees as $musee) {
            // Utilisez le nom du musée comme clé et l'ID du musée comme valeur
            $choices[$musee->getNomMusee()] = $musee->getIdMusee();
        }
        
        // Parcourir chaque réservation et récupérer le nom du musée correspondant
        foreach ($reservationMusees as $reservationMusee) {
            $idMusee = $reservationMusee->getIdMusee();
            $musee = $entityManager->getRepository(Musee::class)->find($idMusee);
            
            // Ajouter le nom du musée au tableau avec la clé correspondant à l'ID de la réservation
            $museeDetails[$reservationMusee->getIdMusee()] = $musee ? $musee->getNomMusee() : 'Nom du musée non trouvé';
        }
    
        return $this->render('reservation_musee/index.html.twig', [
            'reservation_musees' => $reservationMusees,
            'musee_details' => $museeDetails, // Passer les détails du musée à la vue
            'choices' => $choices,
        ]);
        }
  
    #[Route('/new/{id}', name: 'app_reservation_musee_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager, $id,MailerInterface $mailer): Response
    {
        
        // Récupérer l'objet Musée correspondant à l'ID fourni dans l'URL
        $musee = $entityManager->getRepository(Musee::class)->find($id);
    
        // Créer une nouvelle instance de la réservation de musée
        $reservationMusee = new ReservationMusee();
        
        // Lier le musée à la réservation en passant l'ID du musée
        $reservationMusee->setIdMusee($musee->getIdMusee());
        $idUser = 20;
        $reservationMusee->setIdUser($idUser);
    
        // Créer le formulaire en passant l'objet ReservationMusee et l'ID du musée
        $form = $this->createForm(ReservationMuseeType::class, $reservationMusee, ['idMusee' => $id]);
    
        // Gérer la soumission du formulaire
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Traiter les données du formulaire
            $entityManager->persist($reservationMusee);
            
            // Diminuer le nombre de tickets disponibles dans l'entité Musee
            $nbrTicketsReserves = $reservationMusee->getNbrTicketsReserves();
            $musee->setNbrTicketsDisponibles($musee->getNbrTicketsDisponibles() - $nbrTicketsReserves);
    
            $entityManager->flush();
       
            $message = (new Email())
            ->from('yasmine.laajailia@esprit.tn')
            ->to('kassem.benhenda@esprit.tn')
            ->subject('Reservation')
            ->text('Votre reservation est confirmé');

            $mailer->send($message);
            dump('code23');
    
            // Rediriger l'utilisateur vers la page des musées après avoir effectué la réservation
            return $this->redirectToRoute('app_musee_frontoffice');
        }
    
        return $this->renderForm('reservation_musee/new.html.twig', [
            'form' => $form,
        ]);
    }



    #[Route('/{reservationId}', name: 'app_reservation_musee_show', methods: ['GET'])]
    public function show(ReservationMusee $reservationMusee, EntityManagerInterface $entityManager): Response
    {
        // Récupérer l'ID du musée depuis l'entité ReservationMusee
        $idMusee = $reservationMusee->getIdMusee();
        
        // Récupérer les détails du musée correspondant
        $musee = $entityManager->getRepository(Musee::class)->find($idMusee);
    
        return $this->render('reservation_musee/show.html.twig', [
            'reservation_musee' => $reservationMusee,
            'musee' => $musee, // Passer les détails du musée à la vue
        ]);
    }

    #[Route('/{reservationId}/edit', name: 'app_reservation_musee_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, ReservationMusee $reservationMusee, EntityManagerInterface $entityManager): Response
    {
        // Créer le formulaire en passant l'objet ReservationMusee
        $form = $this->createForm(Reservationmusee1Type::class, $reservationMusee);
        
        // Gérer la soumission du formulaire
        $form->handleRequest($request);
        
        if ($form->isSubmitted() && $form->isValid()) {
            // Calculer la différence entre le nombre de tickets réservés initialement et le nouveau nombre de tickets réservés
            $nbrTicketsReservesInitial = $reservationMusee->getNbrTicketsReserves();
            $nbrTicketsReservesNouveau = $reservationMusee->getNbrTicketsReserves();
            $diffTicketsReserves = $nbrTicketsReservesNouveau - $nbrTicketsReservesInitial;
            
            // Récupérer l'objet Musée associé à la réservation
            $idMusee = $reservationMusee->getIdMusee();
            $musee = $entityManager->getRepository(Musee::class)->find($idMusee);
            // Mettre à jour le nombre de tickets disponibles dans l'entité Musee en fonction de la différence
            $musee->setNbrTicketsDisponibles($musee->getNbrTicketsDisponibles() + $diffTicketsReserves);
            
            // Flush pour enregistrer les modifications
            $entityManager->flush();
            flash()->addSuccess('Reservation modifié avec succées!');

            // Redirection vers la page d'index des réservations
            return $this->redirectToRoute('app_reservation_musee_index', [], Response::HTTP_SEE_OTHER);
        }
        
        return $this->renderForm('reservation_musee/edit.html.twig', [
            'reservation_musee' => $reservationMusee,
            'form' => $form,
        ]);
    }


    #[Route('/{reservationId}', name: 'app_reservation_musee_delete', methods: ['POST'])]
    public function delete(Request $request, ReservationMusee $reservationMusee, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$reservationMusee->getReservationId(), $request->request->get('_token'))) {
            $entityManager->remove($reservationMusee);
            $entityManager->flush();
            flash()->addSuccess('Reservation supprimée avec succées!');

        }

        return $this->redirectToRoute('app_reservation_musee_index', [], Response::HTTP_SEE_OTHER);
    }

}
