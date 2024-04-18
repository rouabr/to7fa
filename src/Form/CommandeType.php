<?php

namespace App\Form;

use App\Entity\Commande;
use App\Entity\Oeuvre;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\TextareaType; 

use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;



class CommandeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
         
           

            // Inside the buildForm method
            ->add('nameCommande', TextType::class, [
        
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Please enter a command name.']),
                    new Assert\Regex([
                        'pattern' => '/^[a-zA-Z]+$/',
                        'message' => 'Only alphabetic characters are allowed.'
                    ]),
                 
                    // Add more constraints as needed
                ],
            ])
            ->add('date_commande', DateType::class, [ // Specify DateType for the date field
                'widget' => 'single_text', // This will render the date input as a single text box
                // You can add more options for DateType if needed
            ])
            ->add('prixCommande', NumberType::class, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Please enter a price.']),
                    new Assert\Positive(['message' => 'Price must be a positive number.']),
                    new Assert\Type([
                        'type'    => 'float',
                        'message' => 'The value {{ value }} is not a valid {{ type }}.',
                    ]),
                    // Add more constraints as needed
                ],
                'scale' => 1, // Allow one decimal place
            ])
            ->add('paiement', TextareaType::class, [
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Please enter a payment code.']),
                    // Add more constraints as needed
                ],
                // Other options for the textarea type
            ])
            ->add('id_oeuvre', EntityType::class, [
                'class' => Oeuvre::class,
                'choice_label' => 'titre',
                'constraints' => [
                    new Assert\NotBlank(['message' => 'Please select an artwork.']),
                    // Add more constraints as needed
                ],
                // Other options for the EntityType
            ]);
            
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Commande::class,
        ]);
    }
}
