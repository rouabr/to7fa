<?php

namespace App\Form;

use App\Entity\ParticipationEvenement;
use App\Entity\Evenement;
use Symfony\Component\Form\AbstractType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\NumberType;

class ParticipationEvenementType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nombreParticipation', NumberType::class)
            ->add('numTel')
            ->add('idEvent',EntityType::class,[
                'class' => Evenement::class,
                'choice_label' => 'nomEvent',
                ]
            );
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => ParticipationEvenement::class,
        ]);
    }
}
