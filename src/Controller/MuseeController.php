<?php

namespace App\Controller;

use App\Entity\Musee;
use App\Form\MuseeType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

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
    public function frontoffice(EntityManagerInterface $entityManager): Response
    {
        $musees = $entityManager->getRepository(Musee::class)->findAll();
    
        return $this->render('musee/frontoffice.html.twig', [
            'musees' => $musees,
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

            return $this->redirectToRoute('app_musee_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('musee/new.html.twig', [
            'musee' => $musee,
            'form' => $form,
        ]);
    }

    #[Route('/{idMusee}', name: 'app_musee_show', methods: ['GET'])]
    public function show(Musee $musee): Response
    {
        return $this->render('musee/show.html.twig', [
            'musee' => $musee,
        ]);
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
        }

        return $this->redirectToRoute('app_musee_index', [], Response::HTTP_SEE_OTHER);
    }
}
