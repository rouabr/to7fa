<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Entity\Panier;
use App\Form\PanierType;
use App\Form\CommandeType;
use App\Form\OeuvreType;
use App\Entity\Oeuvre;
use App\Entity\User;
use App\Repository\OeuvreRepository;
use App\Repository\UserRepository;
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

class FrontPanierCommandeController extends AbstractController
{
    #[Route('/front/panier/commande', name: 'app_front_panier_commande')]
    public function index(): Response
    {
        return $this->render('front/index.html.twig', [
            'controller_name' => 'FrontPanierCommandeController',
        ]);
    }
    #[Route('/Front/{page_path}', name: 'app_test55')]
    public function index5($page_path): Response
    {
        
        $file_path = "front/".$page_path; // might be exploiatable !
        // dd($this->getParameter('kernel.project_dir'));
        if (file_exists($this->getParameter('kernel.project_dir').'/templates/'.$file_path)) {
            $entityManager = $this->getDoctrine()->getManager();
            $data = $entityManager->getRepository(Oeuvre::class)->findAll();
            $data_commande = $entityManager->getRepository(Commande::class)->findAll();
            $data_Panier = $entityManager->getRepository(Panier::class)->findAll();
    
            return $this->render($file_path, [ 'paniers' => $data_Panier,
                'oeuvres' => $data,
            'data_commande' => $data_commande,
                                            ]);
        } else {
            return $this->render('dashboard/404.html.twig', []);
        }
    }

    #[Route('/delete_front/{idPanier}', name: 'app_panierdelete', methods: ['POST'])]
    public function delete2(Request $request, $idPanier, ManagerRegistry $manager, PanierRepository $panierRepository): Response
    {
        $em = $manager->getManager();
        $panier = $panierRepository->find($idPanier);

        $em->remove($panier);
        $em->flush();
        
        return new RedirectResponse($this->generateUrl('app_test55', ['page_path' => 'ticket.html.twig']));
    }

    #[Route('/add-to-cart/{id}', name: 'add_to_cart')]
    public function addToCart(Request $request, $id, ManagerRegistry $manager, OeuvreRepository $oeuvreRepository,UserRepository $userRepository ): Response
    {
        $quantity = $request->request->get('quantity');  
        
        $em = $manager->getManager();
        $oeuvre = $oeuvreRepository->find($id);
        $user = $userRepository->find(1);

        $panierItem = new Panier();
        
        $panierItem->setIdOeuvre($oeuvre);
        $panierItem->setIdUser($user);
        $panierItem->setQuantity($quantity);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($panierItem);
        $entityManager->flush();

        // Redirect back to the boutique page
        return $this->redirectToRoute('app_test55', ['page_path' => 'gallery.html.twig']);
    }
    #[Route('/afficher', name: 'aff_index2', methods: ['GET'])]
    public function index2(PanierRepository $paniier): Response
    { 
        return $this->render('panier.html.twig', [
            'paniers' => $paniier->findAllWithOeuvreImages(),
        ]);
    }

    #[Route('/add_commande/{id}/{quantity}/{id_panier}', name: 'add_commande')]
    public function addCommande(Request $request, $id, $quantity, $id_panier, ManagerRegistry $manager, OeuvreRepository $oeuvreRepository,UserRepository $userRepository,PanierRepository $panierRepository ): Response
    {
       // $quantity = $request->request->get('quantity');  
        
        $em = $manager->getManager();
        $oeuvre = $oeuvreRepository->find($id);

        $commande = new Commande();
        $commande->setIdOeuvre($oeuvre);
        $commande->setNameCommande($oeuvre->getTitre());
        $commande->setDateCommande($oeuvre->getDate());
        $commande->setPrixCommande($oeuvre->getPrix()*$quantity);
        $commande->setPaiement("aa");
        $commande->setQuantity($quantity);
        
        

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($commande);
        $entityManager->flush();

        $panier = $panierRepository->find($id_panier);
        $em = $manager->getManager();
        $em->remove($panier);
        $em->flush();

        // Redirect back to the boutique page
        return $this->redirectToRoute('app_test55', ['page_path' => 'ticket.html.twig']);
    }
    #[Route('/{idOeuvre}/add_commande_shio', name: 'app_commande_new_shop', methods: ['GET', 'POST'])]
    public function addCommande_shop(Request $request, $idOeuvre,ManagerRegistry $manager, OeuvreRepository $oeuvreRepository): Response
    {
       // Retrieve the Oeuvre object based on the provided $idOeuvre
    $oeuvre = $this->getDoctrine()->getRepository(Oeuvre::class)->find($idOeuvre);
    $quantity = $request->request->get('quantity'); 

    // Check if the Oeuvre object exists
    if (!$oeuvre) {
        throw $this->createNotFoundException('The oeuvre does not exist');
    }

        return $this->render('front/add_commande.html.twig', [
           'oeuvre'=> $oeuvre,
            'quantity'=> $quantity,
            // You can pass more data to the template if needed
        ]);
    }



    #[Route('/{idOeuvre}/{quantity}/add_commande_to_shop', name: 'app_commande_To_shop', methods: ['GET', 'POST'])]
    public function addCommande_to_shop(Request $request, $idOeuvre, $quantity): Response
{
    // Retrieve the Oeuvre object based on the provided $idOeuvre
    $oeuvre = $this->getDoctrine()->getRepository(Oeuvre::class)->find($idOeuvre);

    // Check if the Oeuvre object exists
    if (!$oeuvre) {
        throw $this->createNotFoundException('The oeuvre does not exist');
    }
    $paymentCode = $request->request->get('paymentCode');

    // Check if $paymentCode is null
    if ($paymentCode === null) {
        // Handle the case where $paymentCode is null (e.g., provide a default value or return an error)
        // Example:
        return new Response('Payment code is missing');
    }
 
    // Create a new Commande instance and populate it with data from the Oeuvre
    $commande = new Commande();
    $commande->setNameCommande($oeuvre->getTitre());
    // Set other fields of Commande from Oeuvre as needed
    $commande->setPrixCommande($oeuvre->getPrix());
    // Set the date_commande to the current date
    $commande->setDateCommande(new \DateTime());

    // You can also associate the Oeuvre directly with the Commande
    $commande->setIdOeuvre($oeuvre);
    $commande->setPaiement($paymentCode);
    $commande->setQuantity($quantity);

    // Persist the Commande entity
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($commande);
    $entityManager->flush();

    // Redirect or render success page
    // Example:
    return $this->redirectToRoute('app_test55', ['page_path' => 'gallery.html.twig']);
}

#[Route('/{qunatity}/{total}/add_all_commande_', name: 'app_all_com', methods: ['GET', 'POST'])]
public function add_all($qunatity, $total): Response
{
    

    return $this->render('front/add_all_commande.html.twig', [
        'quantity'=> $qunatity,
        'total'=> $total,
        // You can pass more data to the template if needed
    ]);
}


#[Route('/add_allcommande_to_shop', name: 'app_allcommande_To_shop', methods: ['GET', 'POST'])]
public function add_allCommande_to_shop(Request $request,ManagerRegistry $manager): Response
{

   

$paymentCode = $request->request->get('paymentCode');

if ($paymentCode === null) {
    
    return new Response('Payment code is missing');
}

$entityManager = $this->getDoctrine()->getManager();


$paniers = $entityManager->getRepository(Panier::class)->findAll();
$i=0;
foreach ($paniers as $panier) {
 $i++;
    $idOeuvre = $panier->getIdOeuvre();
    $quantity = $panier->getQuantity();

    $oeuvre = $entityManager->getRepository(Oeuvre::class)->find($idOeuvre);

    if ($oeuvre) {
        $commande = new Commande();
        $oeuvreName = $oeuvre->getTitre();
        $oeuvrePrice = $oeuvre->getPrix();
        $commande->setNameCommande($oeuvreName);
        // Set other fields of Commande from Oeuvre as needed
        $commande->setPrixCommande($oeuvrePrice * $quantity );
        // Set the date_commande to the current date
        $commande->setDateCommande(new \DateTime());
    
        // You can also associate the Oeuvre directly with the Commande
        $commande->setIdOeuvre($oeuvre);
        $commande->setPaiement($paymentCode);
        $commande->setQuantity($quantity);

     
$entityManager->persist($commande);


$entityManager->remove($panier);

      
    }
}
$entityManager->flush();


// Persist the Commande entity


// Redirect or render success page
// Example:

return $this->redirectToRoute('app_test55', ['page_path' => 'gallery.html.twig']);
}

 


}
