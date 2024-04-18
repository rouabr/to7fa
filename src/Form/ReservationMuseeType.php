<?php

namespace App\Form;

use App\Entity\ReservationMusee;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class ReservationMuseeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('dateReservation', DateType::class, [
                'label' => 'Date de réservation',
                'widget' => 'single_text',
                'html5' => true,
                'format' => 'yyyy-MM-dd',
                'attr' => ['class' => 'form-control datepicker'],
                'label_attr' => ['class' => 'form-label'],
                'placeholder' => 'Sélectionnez une date',
            ])
            ->add('nbrTicketsReserves', null, [
                'label' => 'Nombre de tickets à réserver',
                'attr' => ['class' => 'form-control'],
                'label_attr' => ['class' => 'form-label'],
            ])
            ->add('idUser', HiddenType::class)
            ->add('idMusee', HiddenType::class, [
                'data' => $options['idMusee'],
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => ReservationMusee::class,
            'idMusee' => null,
        ]);
    }
}