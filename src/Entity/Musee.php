<?php

namespace App\Entity;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\ORM\Mapping as ORM;

/**
 * Musee
 *
 * @ORM\Table(name="musee")
 * @ORM\Entity
 * @UniqueEntity(fields={"nomMusee"}, message="Ce nom de musée est déjà utilisé.")
 */
class Musee
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_musee", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idMusee;


  
        /**
         * @var string
         *
         * @ORM\Column(name="nom_musee", type="string", length=255, nullable=false)
         * @Assert\NotBlank(message="Le nom du musée ne peut pas être vide")
         * @Assert\Length(max=5, maxMessage="Le nom du musée ne peut pas dépasser {{ limit }} caractères")
         */
        private $nomMusee;
    
        /**
         * @var string
         *
         * @ORM\Column(name="adresse", type="string", length=255, nullable=false)
         * @Assert\NotBlank(message="L'adresse ne peut pas être vide")
         * @Assert\Length(max=255, maxMessage="L'adresse ne peut pas dépasser {{ limit }} caractères")
         */
        private $adresse;
    
        /**
         * @var string
         *
         * @ORM\Column(name="ville", type="string", length=255, nullable=false)
         * @Assert\NotBlank(message="La ville ne peut pas être vide")
         * @Assert\Length(max=255, maxMessage="La ville ne peut pas dépasser {{ limit }} caractères")
         */
        private $ville;
    
       /**
 * @var int
 *
 * @ORM\Column(name="nbr_tickets_disponibles", type="integer", nullable=false)
 * @Assert\NotBlank(message="Le nombre de tickets disponibles ne peut pas être vide")
 * @Assert\PositiveOrZero(message="Le nombre de tickets disponibles doit être positif ou zéro")
 * @Assert\LessThanOrEqual(value=100, message="Le nombre de tickets disponibles ne peut pas dépasser 100")
 */
private $nbrTicketsDisponibles;
    
     /**
 * @var string
 *
 * @ORM\Column(name="description", type="string", length=255, nullable=false)
 * @Assert\NotBlank(message="La description ne peut pas être vide")
 * @Assert\Length(max=255, maxMessage="La description ne peut pas dépasser {{ 50 }} caractères")
 * @Assert\Regex(
 *      pattern="/\d/",
 *      match=false,
 *      message="La description ne doit pas contenir de nombres"
 * )
 */
        private $description;
    
        /**
         * @var string
         *
         * @ORM\Column(name="image_musee", type="string", length=255, nullable=false)
         */
        private $imageMusee;

    public function getIdMusee(): ?int
    {
        return $this->idMusee;
    }

    public function getNomMusee(): ?string
    {
        return $this->nomMusee;
    }

    public function setNomMusee(string $nomMusee): static
    {
        $this->nomMusee = $nomMusee;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): static
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getVille(): ?string
    {
        return $this->ville;
    }

    public function setVille(string $ville): static
    {
        $this->ville = $ville;

        return $this;
    }

    public function getNbrTicketsDisponibles(): ?int
    {
        return $this->nbrTicketsDisponibles;
    }

    public function setNbrTicketsDisponibles(int $nbrTicketsDisponibles): static
    {
        $this->nbrTicketsDisponibles = $nbrTicketsDisponibles;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): static
    {
        $this->description = $description;

        return $this;
    }

    public function getImageMusee(): ?string
    {
        return $this->imageMusee;
    }

    public function setImageMusee(string $imageMusee): static
    {
        $this->imageMusee = $imageMusee;

        return $this;
    }


}
