<?php

namespace App\Controller;

use App\Entity\Musee;
use App\Entity\User;

use App\Entity\ReservationMusee;
use App\Form\MuseeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

#[Route('/musee')]
class MuseeController extends AbstractController
{
    #[Route('/', name: 'app_musee_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $musees = $entityManager
            ->getRepository(Musee::class)
            ->findAll();

        return $this->render('musee/index.html.twig', [
            'musees' => $musees,
        ]);
    }
    #[Route('/musees', name: 'app_musee_frontoffice', methods: ['GET'])]
    public function frontoffice(Request $request, PaginatorInterface $paginator, EntityManagerInterface $entityManager): Response
    {
        // Créer un formulaire de recherche
        $searchForm = $this->createFormBuilder(null)
            ->add('keyword', TextType::class, [
                'label' => 'Mot-clé de recherche',
                'required' => false, // Le champ n'est pas obligatoire
            ])
            ->add('search', SubmitType::class, [
                'label' => 'Rechercher',
            ])
            ->getForm();
        
        // Traiter le formulaire de recherche s'il est soumis
        $searchForm->handleRequest($request);
        
        // Initialiser la requête pour récupérer tous les musées
        $query = $entityManager->getRepository(Musee::class)->createQueryBuilder('m');
        
        // Ajouter la logique de recherche si le formulaire est soumis et valide
        if ($searchForm->isSubmitted() && $searchForm->isValid()) {
            $formData = $searchForm->getData(); // Récupérer les données du formulaire
            
            // Récupérer le mot-clé de recherche
            $keyword = $formData['keyword'];
            
            // Ajouter une condition WHERE à la requête pour filtrer les musées par le mot-clé
            if (!empty($keyword)) {
                $query->andWhere('m.nomMusee LIKE :keyword OR m.ville LIKE :keyword')
                    ->setParameter('keyword', '%'.$keyword.'%');
            }
        }
        
        // Paginer les données des musées
        $musees = $paginator->paginate(
            $query->getQuery(), // Obtenir la requête finalisée à partir du QueryBuilder
            $request->query->getInt('page', 1), 
            6 // Nombre d'éléments par page
        );
    
        return $this->render('musee/frontoffice.html.twig', [
            'musees' => $musees,
            'searchForm' => $searchForm->createView(), // Passer le formulaire de recherche au template Twig
        ]);
    }
    #[Route('/new', name: 'app_musee_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $musee = new Musee();
        $latitude = $request->query->get('latitude');
        $longitude = $request->query->get('longitude');
    
        // Assurez-vous que les valeurs sont bien des nombres décimaux
        $latitude = floatval($latitude);
        $longitude = floatval($longitude);
    
        // Affecter les valeurs à l'entité Musee
        $musee->setLat($latitude);
        $musee->setLon($longitude);
        $form = $this->createForm(MuseeType::class, $musee);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
             /** @var UploadedFile|null $imageFile */
        $imageFile = $form->get('image_musee')->getData();

        if ($imageFile) {
            $imageName = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
            $imagePath = 'photos/' . $imageName . '.png';
            $musee->setImageMusee($imagePath);
        
        }
            $entityManager->persist($musee);
            $entityManager->flush();
            flash()->addSuccess('Musee ajouté avec succées!');

            return $this->redirectToRoute('app_musee_index', [], Response::HTTP_SEE_OTHER);

        }

        return $this->renderForm('musee/new.html.twig', [
            'musee' => $musee,
            'form' => $form,
        ]);
    }

    #[Route('/{idMusee}', name: 'app_musee_show', methods: ['GET'])]
    public function show(Musee $musee,EntityManagerInterface $entityManager): Response
    {
        $reservations =$entityManager->getRepository(ReservationMusee::class)->findBy(['idMusee' => $musee->getIdMusee()]);
        return $this->render('musee/show.html.twig', [
            'musee' => $musee,
            'reservations' => $reservations,
        ]);
    }
    #[Route('/fc/load/events', name: 'fc_load_events', methods: ['POST'])]
public function loadEvents(Request $request, EntityManagerInterface $entityManager): Response
{
    // Retrieve the date from the request body
    $requestData = json_decode($request->getContent(), true);
    $date = $requestData['date']; // Assuming the date is passed in the 'date' field

    // Fetch reservations for the specified date
    $reservations = $entityManager->getRepository(ReservationMusee::class)->findByDate($date);

    // Return the reservations as JSON response
    return $this->json($reservations);
}
    #[Route('/musee-front/{idMusee}', name: 'app_musee_show_front', methods: ['GET'])]
    public function showfront(Musee $musee): Response
    {
        return $this->render('musee/showMuseeFront.html.twig', [
            'musee' => $musee,
        ]);
    }

    #[Route('/{idMusee}/edit', name: 'app_musee_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Musee $musee, EntityManagerInterface $entityManager): Response
    {
        $latitude = $request->query->get('latitude');
        $longitude = $request->query->get('longitude');
    
        // Assurez-vous que les valeurs sont bien des nombres décimaux
        $latitude = floatval($latitude);
        $longitude = floatval($longitude);
    
        // Affecter les valeurs à l'entité Musee
        $musee->setLat($latitude);
        $musee->setLon($longitude);
        $form = $this->createForm(MuseeType::class, $musee);
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Traitement de l'image
            $imageFile = $form->get('image_musee')->getData();
            if ($imageFile) {
                // Gérer l'enregistrement de l'image, par exemple :
                $imageName = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                $imagePath = 'photos/' . $imageName . '.png';
                $musee->setImageMusee($imagePath);
            }
    
            $entityManager->flush();
            flash()->addSuccess('Musee modifié avec succées!');

            return $this->redirectToRoute('app_musee_index', [], Response::HTTP_SEE_OTHER);

        }
    
        return $this->renderForm('musee/edit.html.twig', [
            'musee' => $musee,
            'form' => $form,
        ]);
    }

    #[Route('/{idMusee}', name: 'app_musee_delete', methods: ['POST'])]
    public function delete(Request $request, Musee $musee, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$musee->getIdMusee(), $request->request->get('_token'))) {
            $entityManager->remove($musee);
            $entityManager->flush();
            flash()->addSuccess('Musee supprimé avec succées!');
        }

        return $this->redirectToRoute('app_musee_index', [], Response::HTTP_SEE_OTHER);
    }
    #[Route('/musee/{id}/generate-pdf', name: 'musee_generate_pdf')]
    public function generatePdf(Musee $musee, EntityManagerInterface $entityManager): Response
    {

        
        // Récupérer la liste des réservations pour le musée dans la journée en cours
        $currentDate = new \DateTime();
        $currentDateString = $currentDate->format('Y-m-d');
        
        $reservations = $entityManager->getRepository(ReservationMusee::class)->findBy([
            'idMusee' => $musee->getIdMusee(), // Utilisez le champ correct 'idMusee'
            'dateReservation' => $currentDateString // Utilisez la chaîne formatée de la date
        ]);
    
        // Récupérer les détails des utilisateurs pour chaque réservation
        $userDetails = [];
        foreach ($reservations as $reservation) {
            $idUser = $reservation->getIdUser();
            $user = $entityManager->getRepository(User::class)->find($idUser);
            $userDetails[$idUser] = $user ? $user->getUserName() : 'Nom du user non trouvé';
        }
    
        // Créer un objet Dompdf
        $options = new Options();
        $options->set('defaultFont', 'Arial');
        $dompdf = new Dompdf($options);
    
        // Générer le contenu HTML pour le PDF en incluant les détails des réservations et des utilisateurs
        $html = $this->renderView('musee/reservations_pdf.html.twig', [
            'musee' => $musee,
            'reservations' => $reservations,
            'userDetails' => $userDetails// Passer les détails des utilisateurs à la vue
        ]);
    
        // Charger le contenu HTML dans Dompdf
        $dompdf->loadHtml($html);
    
        // Générer le PDF
        $dompdf->render();
    
        // Renvoyer le PDF en réponse
        return new Response($dompdf->output(), 200, [
            'Content-Type' => 'application/pdf',
        ]);
    }
    #[Route('/musees/search', name: 'app_musee_search', methods: ['GET', 'POST'])]
    public function search(Request $request, EntityManagerInterface $entityManager, PaginatorInterface $paginator): Response
    {
        $keyword = $request->request->get('keyword');
        $sort = $request->query->get('sort');
    
        // Créez votre requête de recherche
        $query = $entityManager->getRepository(Musee::class)->createQueryBuilder('m');
    
        // Appliquer la condition de recherche si un mot-clé est fourni
        if (!empty($keyword)) {
            $query->andWhere('m.nomMusee LIKE :keyword OR m.ville LIKE :keyword')
                ->setParameter('keyword', '%'.$keyword.'%');
        }
    
        // Appliquer le tri si une option de tri est sélectionnée
        if ($sort) {
            $this->applySort($query, $sort);
        }
    
        // Paginer les résultats de la recherche
        $pagination = $paginator->paginate(
            $query->getQuery(), // Obtenir la requête finalisée à partir du QueryBuilder
            $request->query->getInt('page', 1), // Numéro de page par défaut si aucun numéro n'est spécifié dans l'URL
            6 // Nombre d'éléments par page
        );
    
        // Passer les résultats de la recherche paginés au template approprié pour affichage
        return $this->render('musee/frontoffice.html.twig', [
            'musees' => $pagination,
            'keyword' => $keyword, // Passer le mot-clé de recherche au template pour l'utiliser dans les liens de pagination
        ]);
    }
/**
 * Appliquer le tri à la requête de recherche en fonction du critère donné.
 *
 * @param $query La requête de recherche
 * @param string|null $sort Le critère de tri
 * @return void
 */
private function applySort($query, ?string $sort): void
{
    if ($sort === 'tickets') {
        $query->orderBy('m.ticketsDisponibles', 'DESC');
    } else {
        $query->orderBy('m.nomMusee', 'ASC');
    }
}
    
}
