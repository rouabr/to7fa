<?php

namespace App\Form;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Musee;
use App\Entity\ReservationMusee;
use Symfony\Component\Form\Extension\Core\Type\TextType;

class Reservationmusee1Type extends AbstractType
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('dateReservation', DateType::class, [
            'label' => 'Date de réservation',
            'attr' => [
                'class' => 'form-control date-reservation',
                
            ],
            'html5' => false, // Set to false to use the widget format
            'data' => new \DateTime(), // Set the default date to the current date
        ])
            ->add('nbrTicketsReserves', IntegerType::class, [
                'label' => 'Nombre de tickets réservés',
                'attr' => [
                    'class' => 'form-control',
                    'placeholder' => 'Entrez le nombre de tickets réservés'
                ],
            ])
            ->add('idMusee', ChoiceType::class, [
                'label' => 'Musée',
                'choices' => $this->getMuseeChoices(),
                'attr' => [
                    'class' => 'form-control',
                ],
            ]);
    }

    private function getMuseeChoices(): array
    {
        $musees = $this->entityManager->getRepository(Musee::class)->findAll();
        $choices = [];
        foreach ($musees as $musee) {
            // Utilisez l'ID du musée comme clé et le nom du musée comme valeur
            $choices[$musee->getNomMusee()] = $musee->getIdMusee();
        }
        return $choices;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => ReservationMusee::class,
        ]);
    }
}