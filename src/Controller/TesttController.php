<?php


namespace App\Controller;


use App\Entity\ParticipationEvenement;
use App\Form\ParticipationEvenementType;
use App\Entity\Evenement;
use App\Form\EvenementType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;

use Twilio\Rest\Client;


class TesttController extends AbstractController
{
    /**
     * @Route("/front", name="app_evenement_newnn", methods={"GET", "POST"})
     */
    public function front(Request $request, EntityManagerInterface $entityManager): Response
    {
        $evenements = $entityManager
        ->getRepository(Evenement::class)
        ->findAll();
        return $this->render('front/event.html.twig', [
            'evenements' => $evenements,
        ]);
    }

    /**
     * @Route("/reserver", name="app_participation_evenement_new_front", methods={"GET", "POST"})
     */
    public function reserver(Request $request, EntityManagerInterface $entityManager): Response
    {
        $participationEvenement = new ParticipationEvenement();
        $form = $this->createForm(ParticipationEvenementType::class, $participationEvenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $eventName = $participationEvenement->getIdEvent();
            $entityManager->persist($participationEvenement);
            $entityManager->flush();

            $telephone = '+216' . $participationEvenement->getNumTel();
    
            // Envoyer un SMS avec Twilio
            $twilioSid = "ACce88d5d034e3f2e921ca6ca4b82bad62";
            $twilioToken = "17745598b6412468d5b73b06de88f268";
            $twilioNumber =  "+12406522732";
        
            $client = new Client($twilioSid, $twilioToken);
            $message = "Participation confirmée.";
        
            $client->messages->create(
                $telephone, // Numéro de téléphone du destinataire avec le préfixe de pays "+216"
                [
                    "from" => $twilioNumber,
                    "body" => $message,
                ]
            );
            echo "Le SMS a été envoyé avec succès à " . $telephone;
            

            return $this->redirectToRoute('app_evenement_newnn', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('front/reserver.html.twig', [
            'participation_evenement' => $participationEvenement,
            'form' => $form,
        ]);
    }
}

