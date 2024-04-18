<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Panier;
use App\Form\PanierType;
use App\Form\CommandeType;
use App\Entity\Oeuvre;
use App\Entity\User;

use App\Repository\CommandeRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Doctrine\ORM\EntityManagerInterface;
use App\Repository\PanierRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Form\Extension\Core\Type\DateType;




class PanierController extends AbstractController
{
    #[Route('/Loader/{page_path}', name: 'app_test2')]
    public function index5($page_path): Response
    {
        
        $file_path = "dashboard/".$page_path; // might be exploiatable !
        // dd($this->getParameter('kernel.project_dir'));
        if (file_exists($this->getParameter('kernel.project_dir').'/templates/'.$file_path)) {
            $entityManager = $this->getDoctrine()->getManager();
            $data = $entityManager->getRepository(Panier::class)->findAll();
            $data_commande = $entityManager->getRepository(Commande::class)->findAll();
    
            return $this->render($file_path, ['data' => $data,
            'data_commande' => $data_commande,
                                            ]);
        } else {
            return $this->render('dashboard/404.html.twig', []);
        }
    }
    
    #[Route('/new_panier', name: 'app_panier_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $panier = new Panier();
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);


        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($panier);
            $entityManager->flush();
            return new RedirectResponse($this->generateUrl('app_test2', ['page_path' => 'simple-tables.html.twig']));     
        }

        return $this->renderForm('dashboard/form_basics.html.twig', [
            'panier' => $panier,
            'form2' => $form,
            
        ]);
    }

    #[Route('/delete/{idPanier}', name: 'app_panier_delete', methods: ['POST'])]
    public function delete2(Request $request, $idPanier, ManagerRegistry $manager, PanierRepository $panierRepository): Response
    {
        $em = $manager->getManager();
        $panier = $panierRepository->find($idPanier);

        $em->remove($panier);
        $em->flush();
        
        return new RedirectResponse($this->generateUrl('app_test2', ['page_path' => 'simple-tables.html.twig']));
    }

     #[Route('/{idPanier}/edit', name: 'app_panier_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, ManagerRegistry $manager, $idPanier, PanierRepository $panierrepository): Response
    {     
        $em = $manager->getManager();

        $panier  = $panierrepository->find($idPanier);
        $form = $this->createForm(PanierType::class, $panier);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($panier);
            $em->flush();

            return $this->redirectToRoute('app_test2', ['page_path' => 'simple-tables.html.twig']);
        }

        return $this->render('dashboard/form_basics_update.html.twig', [
            'panier'=>$panier,
            'form2' => $form->createView(),
        ]);
    }

    //////////////////////////commande //////////////////////////

    #[Route('/delete_commande/{idommande}', name: 'app_commande_delete', methods: ['POST'])]
    public function delete_commande(Request $request, $idommande, ManagerRegistry $manager, CommandeRepository $commandeRepository): Response
    {
        $em = $manager->getManager();
        $commande = $commandeRepository->find($idommande);

        $em->remove($commande);
        $em->flush();
        
        return new RedirectResponse($this->generateUrl('app_test2', ['page_path' => 'simple-tables.html.twig']));
    }

    #[Route('/new_commande', name: 'app_commande_new', methods: ['GET', 'POST'])]
    public function new_commande(Request $request, EntityManagerInterface $entityManager): Response
    {
        $commande = new Commande();
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($commande);
            $entityManager->flush();
            return new RedirectResponse($this->generateUrl('app_test2', ['page_path' => 'simple-tables.html.twig']));
           
        }

        return $this->renderForm('dashboard/form_advanceds.html.twig', [
            'commande' => $commande,
            'form3' => $form,
        ]);
    }

    #[Route('/{idCommande}/edit_commande', name: 'app_commande_edit', methods: ['GET', 'POST'])]
    public function edit_commande(Request $request, ManagerRegistry $manager, $idCommande, CommandeRepository $commanderepository): Response
    {     
        $em = $manager->getManager();

        $commande  = $commanderepository->find($idCommande);
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $em->persist($commande);
            $em->flush();

            return $this->redirectToRoute('app_test2', ['page_path' => 'simple-tables.html.twig']);
        }

        return $this->render('dashboard/update_commande.html.twig', [
            'commande'=>$commande,
            'form3' => $form->createView(),
        ]);
    }
    #[Route('/allnew_commande', name: 'app_all_commande', methods: ['GET', 'POST'])]
    public function all_commande(Request $request, EntityManagerInterface $entityManager): Response
    {
        $commande = new Commande();
        $form = $this->createForm(CommandeType::class, $commande);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($commande);
            $entityManager->flush();
            return new RedirectResponse($this->generateUrl('app_test2', ['page_path' => 'simple-tables.html.twig']));
           
        }

        return $this->renderForm('dashboard/form_advanceds.html.twig', [
            'commande' => $commande,
            'form3' => $form,
        ]);
    }


}
