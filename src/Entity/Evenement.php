<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Evenement
 *
 * @ORM\Table(name="evenement")
 * @ORM\Entity
 */
class Evenement
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_event", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_event", type="string", length=255, nullable=false)
     */
    private $nomEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="description_event", type="string", length=255, nullable=false)
     */
    private $descriptionEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="lieu_event", type="string", length=255, nullable=false)
     */
    private $lieuEvent;

    /**
     * @var bool
     *
     * @ORM\Column(name="type_event", type="boolean", nullable=false)
     */
    private $typeEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateDebut_event", type="date", nullable=false)
     */
    private $datedebutEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateFin_event", type="date", nullable=false)
     */
    private $datefinEvent;

    /**
     * @var int
     *
     * @ORM\Column(name="capacite_event", type="integer", nullable=false)
     */
    private $capaciteEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="image_event", type="string", length=255, nullable=false)
     */
    private $imageEvent;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_event", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixEvent;

    public function getIdEvent(): ?int
    {
        return $this->idEvent;
    }

    public function getNomEvent(): ?string
    {
        return $this->nomEvent;
    }

    public function setNomEvent(string $nomEvent): static
    {
        $this->nomEvent = $nomEvent;

        return $this;
    }

    public function getDescriptionEvent(): ?string
    {
        return $this->descriptionEvent;
    }

    public function setDescriptionEvent(string $descriptionEvent): static
    {
        $this->descriptionEvent = $descriptionEvent;

        return $this;
    }

    public function getLieuEvent(): ?string
    {
        return $this->lieuEvent;
    }

    public function setLieuEvent(string $lieuEvent): static
    {
        $this->lieuEvent = $lieuEvent;

        return $this;
    }

    public function isTypeEvent(): ?bool
    {
        return $this->typeEvent;
    }

    public function setTypeEvent(bool $typeEvent): static
    {
        $this->typeEvent = $typeEvent;

        return $this;
    }

    public function getDatedebutEvent(): ?\DateTimeInterface
    {
        return $this->datedebutEvent;
    }

    public function setDatedebutEvent(\DateTimeInterface $datedebutEvent): static
    {
        $this->datedebutEvent = $datedebutEvent;

        return $this;
    }

    public function getDatefinEvent(): ?\DateTimeInterface
    {
        return $this->datefinEvent;
    }

    public function setDatefinEvent(\DateTimeInterface $datefinEvent): static
    {
        $this->datefinEvent = $datefinEvent;

        return $this;
    }

    public function getCapaciteEvent(): ?int
    {
        return $this->capaciteEvent;
    }

    public function setCapaciteEvent(int $capaciteEvent): static
    {
        $this->capaciteEvent = $capaciteEvent;

        return $this;
    }

    public function getImageEvent(): ?string
    {
        return $this->imageEvent;
    }

    public function setImageEvent(string $imageEvent): static
    {
        $this->imageEvent = $imageEvent;

        return $this;
    }

    public function getPrixEvent(): ?float
    {
        return $this->prixEvent;
    }

    public function setPrixEvent(float $prixEvent): static
    {
        $this->prixEvent = $prixEvent;

        return $this;
    }


}
