<?php
// src/Form/SearchType.php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\SearchType as BaseType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class SearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('searchTerm', BaseType::class, [
                'label' => false, // Hide the label for the search input
                'attr' => [
                    'placeholder' => 'Search by name...', // Placeholder text for the search input
                    'class' => 'form-control', // Optional: add Bootstrap class for styling
                ],
                'required' => false, // Search term is optional
            ]);
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            // Configure your form options here if needed
        ]);
    }
}
