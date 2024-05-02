<?php

namespace App\Form;

use App\Entity\Panier;
use App\Entity\Oeuvre;
use App\Entity\User;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\PositiveOrZero;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Validator\Constraints\Positive;


class PanierType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('id_oeuvre',EntityType::class,[
            'class'=>Oeuvre::class,
            'choice_label'=>'titre',
            'expanded'=>false,
            'multiple'=>false,
        ])
           ->add('id_user',EntityType::class,[
                'class'=>User::class,
                'choice_label'=>'user_name',
                'expanded'=>false,
                'multiple'=>false,
            ])
            ->add('Quantity', IntegerType::class, [
                'constraints' => [
                    new NotBlank(['message' => 'Please enter a quantity.']),
                    new Positive(['message' => 'Quantity must be a positive number.']),
                    // Add any other constraints you want
                ],
                'data' => 0, // Set default value to 0
                'attr' => [
                    'class' => 'form-control',
                    'id' => 'quantityInput',
                ],
            ]);
           
        
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Panier::class,
        ]);
    }
}
