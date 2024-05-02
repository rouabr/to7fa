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
use Stripe\Stripe;
use Stripe\StripeClient;
use Stripe\Checkout\Session;
use Stripe\Price;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use Stripe\Exception\CardException;
use Stripe\PaymentIntent;
use Stripe\Exception\StripeException;
use Stripe\Customer;


class FrontPanierCommandeController extends AbstractController
{
    #[Route('/front/panier/commande', name: 'app_front_panier_commande')]
    public function index(): Response
    {
        return $this->render('front/index.html.twig', [
            'controller_name' => 'FrontPanierCommandeController',
        ]);
    }

    #[Route('/my_commandes', name: 'app_my_commandes')]
    public function my_commandes(CommandeRepository $commandeRepository , UserRepository $userRepository): Response
    {
      
        $user = $userRepository->find(1);
        $allCommands = $commandeRepository->findByUserId(1);

        $commandsData = [];

        // Iterate over each command
        $nb=0;
        foreach ($allCommands as $command) {
            $nb++;
            // Extract relevant data from each command and add it to the array
            $commandsData[] = [
                'commandName' => $command->getNameCommande(). ' in ' . $command->getDateCommande()->format('Y-m-d') . ' , ',
               // Assuming 'getName()' returns the name of the command
                // Add other attributes of the command here as needed
            ];
        }
        $commandsString = implode( array_column($commandsData, 'commandName'));

        $produitData = [
            'mester' =>$user->getUserName() . " you have ".$nb. " commandes with names : " .$commandsString, 
            
        
            // Ajoutez les autres attributs ici selon vos besoins
        ];
        
            
                        // Convertir les données du produit en format JSON
                        $jsonData = json_encode($produitData);
            
                        // Générer l'URL pour l'API du code QR en utilisant les données JSON du produit
                        $qrCodeUrl = 'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=' . urlencode($jsonData);


        return $this->render('front/qrcode.html.twig', [
            'qrCodeUrl' => $qrCodeUrl,
       
        ]);
    }

    #[Route('/recherch', name: 'app_recherch')]
    public function recherch(Request $request,PanierRepository $panierRepository): Response
    {   
      
        $titre = $request->query->get('name');
       
       
        if ($titre !== "") {
            $data_Panier =$panierRepository->findByTitre($titre);
            
          
        } else {
            // Handle the case where no name is provided
            $data_Panier =$panierRepository->findAll();

        }
        return $this->render('front/ticket.html.twig', [
            'paniers' => $data_Panier,
            
           
        ]);
    
      

       
    }

    #[Route('/order', name: 'app_order')]
    public function order(Request $request,PanierRepository $panierRepository): Response
    {   
      
      
            $data_Panier =$panierRepository->findAllOrderedByPrice();
               
        return $this->render('front/ticket.html.twig', [
            'paniers' => $data_Panier,
            
           
        ]);
    
      

       
    }


  

    #[Route('/Front/{page_path}', name: 'app_test55')]
    public function index5($page_path,Request $request): Response
    {
        
        $file_path = "front/".$page_path; // might be exploiatable !
        // dd($this->getParameter('kernel.project_dir'));
        if (file_exists($this->getParameter('kernel.project_dir').'/templates/'.$file_path)) {
            $entityManager = $this->getDoctrine()->getManager();
            $data = $entityManager->getRepository(Oeuvre::class)->findAll();
            $data_commande = $entityManager->getRepository(Commande::class)->findAll();
            $data_Panier = $entityManager->getRepository(Panier::class)->findAll();
              // Retrieve search query from request
        $searchQuery = $request->query->get('q');

        // Fetch data based on search query (if provided)
        if ($searchQuery) {
            $paniers =  $entityManager->getRepository(Panier::class)->findBySearchQuery($searchQuery); // Implement this method in your repository
        } else {
            $paniers = $entityManager->getRepository(Panier::class)->findAll();
        }
    
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
        $commande->setPrixCommande($oeuvre->getPrix()* $quantity);
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
    public function addCommande_shop(Request $request, $idOeuvre,ManagerRegistry $manager, OeuvreRepository $oeuvreRepository,CommandeRepository $com): Response
    {
       // Retrieve the Oeuvre object based on the provided $idOeuvre
    $oeuvre = $this->getDoctrine()->getRepository(Oeuvre::class)->find($idOeuvre);
    $quantity = $request->request->get('quantity'); 

    // Check if the Oeuvre object exists
    if (!$oeuvre) {
        throw $this->createNotFoundException('The oeuvre does not exist');
    }

    $entityManager = $this->getDoctrine()->getManager();
    $userRepository = $entityManager->getRepository(User::class);

    // Find the user with ID 1
    $user = $userRepository->find(1);

    $entityManager = $this->getDoctrine()->getManager();

    $userPurchases = $com->findUserPurchasesWithinYear(1);

    // Calculate the discount based on the number of purchases
    $purchaseCount = count($userPurchases);
    $discount = 0;
    if ($purchaseCount > 5  && $user->isHasdiscount()== false ) {
        $discount = 0.20;
        $user->setHasDiscount(true); 
    }

    $entityManager->persist($user);
$entityManager->flush();
    
    



        return $this->render('front/add_commande.html.twig', [
           'oeuvre'=> $oeuvre,
            'quantity'=> $quantity,
            'discount'=> $discount,
            // You can pass more data to the template if needed
        ]);
    }



    #[Route('/{idOeuvre}/{quantity}/{total}/add_commande_to_shop', name: 'app_commande_To_shop', methods: ['GET', 'POST'])]
    public function addCommande_to_shop(Request $request, $idOeuvre, $quantity, $total, UserRepository $userRepository): Response
{
    // Retrieve the Oeuvre object based on the provided $idOeuvre
    $oeuvre = $this->getDoctrine()->getRepository(Oeuvre::class)->find($idOeuvre);

    // Check if the Oeuvre object exists
    if (!$oeuvre) {
        throw $this->createNotFoundException('The oeuvre does not exist');
    }


  
 
    // Create a new Commande instance and populate it with data from the Oeuvre
    $commande = new Commande();
    $commande->setNameCommande($oeuvre->getTitre());
    // Set other fields of Commande from Oeuvre as needed
    $commande->setPrixCommande($total);
    // Set the date_commande to the current date
    $commande->setDateCommande(new \DateTime());

    // You can also associate the Oeuvre directly with the Commande
    $commande->setIdOeuvre($oeuvre);
    $commande->setPaiement(1);
    $commande->setQuantity($quantity);
    $user = $userRepository->find(1);
    $commande->setIdUser($user);

    // Persist the Commande entity
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($commande);
    $entityManager->flush();

    // Redirect or render success page
    // Example:
    return $this->render('front/confirmation.html.twig',[
        'total'=> $total,
        // You can pass more data to the template if needed
    ]);
}

#[Route('/{qunatity}/{total}/add_all_commande_', name: 'app_all_com', methods: ['GET', 'POST'])]
public function add_all($qunatity, $total ,UserRepository $userRepository ,CommandeRepository $com): Response
{      
    
    $entityManager = $this->getDoctrine()->getManager();
    $userRepository = $entityManager->getRepository(User::class);

    // Find the user with ID 1
    $user = $userRepository->find(1);

    $entityManager = $this->getDoctrine()->getManager();

    $userPurchases = $com->findUserPurchasesWithinYear(1);

    // Calculate the discount based on the number of purchases
    $purchaseCount = count($userPurchases);
    $discount = 0;
    if ($purchaseCount > 5  && $user->isHasdiscount()== false ) {
        $discount = 0.20;
        $user->setHasDiscount(true); 
    }

    $entityManager->persist($user);
$entityManager->flush();
    
    

    return $this->render('front/add_all_commande.html.twig', [
        'quantity'=> $qunatity,
        'total'=> $total,
        'discount'=> $discount,
        // You can pass more data to the template if needed
    ]);
}


#[Route('/{total}/add_allcommande_to_shop', name: 'app_allcommande_To_shop', methods: ['GET', 'POST'])]
public function add_allCommande_to_shop(Request $request,ManagerRegistry $manager,$total): Response
{

   



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
        $commande->setPaiement(1);
        $commande->setQuantity($quantity);

     
$entityManager->persist($commande);


$entityManager->remove($panier);

      
    }
}
$entityManager->flush();


// Persist the Commande entity


// Redirect or render success page
// Example:

return $this->render('front/confirmation.html.twig',[
    'total'=> $total,
    // You can pass more data to the template if needed
]);
}

#[Route('/{total}/confirmation', name: 'app_confirm')]
    public function confirmer_commande(Request $request,$total): Response
    {

        $entityManager = $this->getDoctrine()->getManager();
        $userRepository = $entityManager->getRepository(User::class);
    
        // Find the user with ID 1
        $user = $userRepository->find(2);
        $userEmail = $user->getUserEmail();
        $stripeToken = $request->request->get('stripeToken');
        Stripe::setApiKey('sk_test_51OpqGvDXGjZ4Cjv6NQSsDOKMh9QRwkw15NkS5eJR6ziiBunQwqsamzyBSR3BzN2SYqfb73hb1Y3NsoWcj7A7UVYM00K687EWQi');
        $amountInCents = ceil($total * 100); 

      
            $existingCustomer = Customer::all(['email' => $userEmail, 'limit' => 1])->data;
            if (!empty($existingCustomer)) {
                $customerId = $existingCustomer[0]->id;
            } else {
                try {
            $customer = Customer::create([
                'name' => $user->getUserName(),
                'email' => $user->getUserEmail(), 
                'payment_method' => 'pm_card_visa',// Customer's email address
                // Optionally, you can include other customer details here
            ]);
            $customerId = $customer->id;
        } catch (\Exception $e) {
            // Handle Stripe API errors
            return new JsonResponse(['error' => $e->getMessage()], 500);
        }
        }
       
       
        try {
            $paymentIntent = PaymentIntent::create([
                'customer' => $customerId,
                'amount' => $amountInCents, // Amount in cents
                'currency' => 'usd',
                'payment_method' => 'pm_card_visa',
                'description' => 'succeful'

            ]);
        
            // If the payment was successful, display a success message
            $paymentIntentId = $paymentIntent->id;
            $this->addFlash('success', 'Payment successful. PaymentIntent ID: ' . $paymentIntentId);
        } catch (StripeException $e) {
            // If there was an error processing the payment, display the error message
            $this->addFlash('error', 'Payment failed. Error: ' . $e->getMessage());
        }

        // Return the client secret
        return $this->redirectToRoute('app_test55', ['page_path' => 'gallery.html.twig']);
    }

    }

 



