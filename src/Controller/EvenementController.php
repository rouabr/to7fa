<?php

namespace App\Controller;
require_once(__DIR__ . '/../../vendor/autoload.php');
require_once(__DIR__ . '/../../vendor/cloudmersive/cloudmersive_imagerecognition_api_client/vendor/autoload.php');

use App\Entity\Evenement;
use App\Form\EvenementType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\String\Slugger\SluggerInterface;
use App\Form\SearchType;
use Symfony\Component\HttpFoundation\JsonResponse;
use Swagger\Client;
use Swagger\Client\Configuration;
use Swagger\Client\Api\NsfwApi;
use GuzzleHttp;

use Symfony\Component\Form\Extension\Core\Type\TextType;

/**
 * @Route("/evenement")
 */
class EvenementController extends AbstractController
{
    /**
     * @Route("/", name="app_evenement_index", methods={"GET", "POST"})
     */
    public function index(Request $request, EntityManagerInterface $entityManager): Response
    {
        $searchForm = $this->createFormBuilder(null, [
            'method' => 'POST', // Use POST method for search form
            'action' => $this->generateUrl('app_evenement_index'), // Submit to the same page
        ])
            ->add('searchTerm', TextType::class, [
                'label' => false, // Hide the label for the search input
                'attr' => [
                    'placeholder' => 'Search by name...', // Placeholder text for the search input
                    'class' => 'form-control', // Optional: add Bootstrap class for styling
                ],
                'required' => false, // Search term is optional
            ])
            ->getForm();

        $searchForm->handleRequest($request);

        if ($searchForm->isSubmitted() && $searchForm->isValid()) {
            $searchTerm = $searchForm->get('searchTerm')->getData();

            //var_dump($searchTerm);

            $evenements = $entityManager
                ->getRepository(Evenement::class)
                ->createQueryBuilder('e')
                ->where('e.nomEvent LIKE :searchTerm')
                ->setParameter('searchTerm', '%' . $searchTerm . '%')
                ->getQuery()
                ->getResult();
        } else {
            $evenements = $entityManager
                ->getRepository(Evenement::class)
                ->findAll();
        }

        return $this->render('evenement/index.html.twig', [
            'evenements' => $evenements,
            'searchForm' => $searchForm->createView(),
        ]);
    }

     /**
     * @Route("/new", name="app_evenement_new", methods={"GET", "POST"})
     */
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $imagesDirectory = "uploads/evenements/";
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $imageFile = $form->get('image')->getData(); 
            
            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                
                $safeFilename = strtolower(preg_replace('/[^a-z0-9]/i', '-', $originalFilename));
                $newFilename = $safeFilename . '-' . uniqid() . '.' . $imageFile->guessExtension();

                try {
                    $imageFile->move($imagesDirectory, $newFilename);
                } catch (FileException $e) {
                    //erreur
                }
                
                $evenement->setImageEvent($newFilename);

                
                //Verifier image pour contenu inapproprié

                $config = Configuration::getDefaultConfiguration()->setApiKey('Apikey', '6bf7599a-be63-4363-9678-4549c32d001f');



                $apiInstance = new NsfwApi(
                    
                    
                    new GuzzleHttp\Client(),
                    $config
                );
                $image_file = __DIR__ . "/../../public/uploads/evenements/".$newFilename; // \SplFileObject | Image file to perform the operation on.  Common file formats such as PNG, JPEG are supported.

                try {
                    $result = $apiInstance->nsfwClassify($image_file);
                    if($result->getScore()>0.2)
                    {
                        return $this->renderForm('evenement/new.html.twig', [
                            'evenement' => $evenement,
                            'form' => $form,
                            'nsfw' => true
                        ]);
                    }
                } catch (Exception $e) {
                    echo 'Exception when calling NsfwApi->nsfwClassify: ', $e->getMessage(), PHP_EOL;
                }
            }



            $entityManager->persist($evenement);
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('evenement/new.html.twig', [
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }

    /**
 * @Route("/{id}", name="app_evenement_show", methods={"GET"})
 */
    public function show(Evenement $evenement): Response
    {
        return $this->render('evenement/show.html.twig', [
            'evenements' => $evenement,
            'evenement' => $evenement,
        ]);
    }

  /**
 * @Route("/{idEvent}/edit", name="app_evenement_edit", methods={"GET", "POST"})
 */
    public function edit(Request $request, Evenement $evenement, EntityManagerInterface $entityManager): Response
    {
        $imagesDirectory = "%kernel.project_dir%/public/uploads/evenements/";

        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            
            $imageFile = $form->get('image')->getData(); 
            
            if ($imageFile) {
                $originalFilename = pathinfo($imageFile->getClientOriginalName(), PATHINFO_FILENAME);
                
                $safeFilename = strtolower(preg_replace('/[^a-z0-9]/i', '-', $originalFilename));
                $newFilename = $safeFilename . '-' . uniqid() . '.' . $imageFile->guessExtension();

                try {
                    $imageFile->move($imagesDirectory, $newFilename);
                } catch (FileException $e) {
                    //erreur
                }

                $evenement->setImageEvent($newFilename);
            }
            
            $entityManager->flush();

            return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        }

        

        return $this->renderForm('evenement/edit.html.twig', [
            'evenements' => $evenement,
            'evenement' => $evenement,
            'form' => $form,
        ]);
    }


    /**
     * @Route("/{idEvent}", name="app_evenement_delete", methods={"POST"})
     */
        public function delete(Request $request, Evenement $evenement, EntityManagerInterface $entityManager ,$idEvent): Response
    {

        if ($this->isCsrfTokenValid('delete'.$idEvent, $request->request->get('_token'))) {
            $entityManager->remove($evenement);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_evenement_index', [], Response::HTTP_SEE_OTHER);
        
    }
    




   /**
 * @Route("/search", name="app_evenement_search", methods={"POST"})
 */
public function search(Request $request, EntityManagerInterface $entityManager): JsonResponse
{
    $searchTerm = $request->request->get('searchTerm');

    $evenements = $entityManager
        ->getRepository(Evenement::class)
        ->createQueryBuilder('e')
        ->where('e.nomEvent LIKE :searchTerm')
        ->setParameter('searchTerm', '%' . $searchTerm . '%')
        ->getQuery()
        ->getResult();

    return $this->json(['evenements' => $evenements]);
}
}

