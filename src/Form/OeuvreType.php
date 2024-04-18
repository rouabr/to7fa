<?php

namespace App\Form;

use App\Entity\Oeuvre;
use App\Entity\Categorie;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\DateType;

class OeuvreType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('titre')
            ->add('description')
            ->add('prix')
            ->add('date', DateType::class, [ // Specify DateType for the date field
                'widget' => 'single_text', // This will render the date input as a single text box
                // You can add more options for DateType if needed
            ])
            ->add('status')
            ->add('id_cat',EntityType::class,[
                'class'=>Categorie::class,
                'choice_label'=>'nom_cat',
                'expanded'=>false,
                'multiple'=>false,
            ])
            ->add('lienimg')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Oeuvre::class,
        ]);
    }
}
