<?php

namespace App\Form;

use App\Entity\Musee;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints\File; 
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;

class MuseeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nomMusee', null, [
                'label' => 'Nom du musée',
                'attr' => ['class' => 'form-control']

            ])
            ->add('adresse', null, [
                'label' => 'Adresse',
                'attr' => ['class' => 'form-control']

            ])
            ->add('ville', ChoiceType::class, [
                'label' => 'Ville',
                'choices' => [
                    'Ariana' => 'Ariana',
                    'Béja' => 'Béja',
                    'Ben Arous' => 'Ben Arous',
                    'Bizerte' => 'Bizerte',
                    'Gabes' => 'Gabes',
                    'Gafsa' => 'Gafsa',
                    'Jendouba' => 'Jendouba',
                    'Kairouan' => 'Kairouan',
                    'Kasserine' => 'Kasserine',
                    'Kebili' => 'Kebili',
                    'La Manouba' => 'La Manouba',
                    'Le Kef' => 'Le Kef',
                    'Mahdia' => 'Mahdia',
                    'Médenine' => 'Médenine',
                    'Monastir' => 'Monastir',
                    'Nabeul' => 'Nabeul',
                    'Sfax' => 'Sfax',
                    'Sidi Bouzid' => 'Sidi Bouzid',
                    'Siliana' => 'Siliana',
                    'Sousse' => 'Sousse',
                    'Tataouine' => 'Tataouine',
                    'Tozeur' => 'Tozeur',
                    'Tunis' => 'Tunis',
                    'Zaghouan' => 'Zaghouan',
                ],
                'attr' => ['class' => 'form-control']

                
            ])
            ->add('nbrTicketsDisponibles', null, [
                'label' => 'Nombre des tickets disponibles',
                'attr' => ['class' => 'form-control'],
              

            ])
            ->add('description', null, [
                'label' => 'Description',

                'attr' => ['class' => 'form-control']
            ])
            ->add('image_musee', FileType::class, [
                'label' => 'Image',
                
                'mapped' => false,
                'required' => false,
                'constraints' => [
                    new File([
                        //'maxSize' => '1024k',
                        'mimeTypes' => [
                            'image/gif',
                            'image/jpeg',
                            'image/png',
                            'image/jpg',
                        ],
                        'mimeTypesMessage' => 'Please upload a valid Image',
                    ])
                ],
                'attr' => ['class' => 'form-control-file']
            ])
            ->add('lat', HiddenType::class, [
                'attr' => ['style' => 'display:none;'], // Cachez le champ lat
            ])
            ->add('lon', HiddenType::class, [
                'attr' => ['style' => 'display:none;'], // Cachez le champ lon
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Musee::class,
        ]);
    }
}