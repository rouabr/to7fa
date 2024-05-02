<?php

namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


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
     * @Assert\NotBlank(message="The event name cannot be empty.")
     * @Assert\Length(max=255)
     */
    private $nomEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="description_event", type="string", length=255, nullable=false)
     * @Assert\NotBlank
     * @Assert\Length(min=10, max=255)
     */
    private $descriptionEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="lieu_event", type="string", length=255, nullable=false)
     * @Assert\NotBlank
     * @Assert\Length(max=255)
     */
    private $lieuEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="type_event", type="string", length=50, nullable=false)
     * @Assert\NotBlank
     * @Assert\Length(max=255)
     */
    private $typeEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateDebut_event", type="date", nullable=false)
     * @Assert\NotNull
     */
    private $datedebutEvent;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateFin_event", type="date", nullable=false)
     * @Assert\NotNull
     * @Assert\GreaterThan(propertyPath="datedebutEvent")
     */
    private $datefinEvent;

    /**
     * @var int
     *
     * @ORM\Column(name="capacite_event", type="integer", nullable=false)
     * @Assert\NotNull
     * @Assert\Positive
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
     * @Assert\NotBlank
     * @Assert\PositiveOrZero
     */
    private $prixEvent;

    /**
     * @ORM\OneToMany(targetEntity=Participation::class, mappedBy="evenement", orphanRemoval=true)
     */
    private $participations;

    public function __construct()
    {
        $this->participations = new ArrayCollection();
    }

    public function getIdEvent(): ?int
    {
        return $this->idEvent;
    }

    public function getNomEvent(): ?string
    {
        return $this->nomEvent;
    }

    public function setNomEvent(string $nomEvent): self
    {
        $this->nomEvent = $nomEvent;

        return $this;
    }

    public function getDescriptionEvent(): ?string
    {
        return $this->descriptionEvent;
    }

    public function setDescriptionEvent(string $descriptionEvent): self
    {
        $this->descriptionEvent = $descriptionEvent;

        return $this;
    }

    public function getLieuEvent(): ?string
    {
        return $this->lieuEvent;
    }

    public function setLieuEvent(string $lieuEvent): self
    {
        $this->lieuEvent = $lieuEvent;

        return $this;
    }

    public function getTypeEvent(): ?string
    {
        return $this->typeEvent;
    }

    public function setTypeEvent(string $typeEvent): self
    {
        $this->typeEvent = $typeEvent;

        return $this;
    }

    public function getDatedebutEvent(): ?\DateTimeInterface
    {
        return $this->datedebutEvent;
    }

    public function setDatedebutEvent(\DateTimeInterface $datedebutEvent): self
    {
        $this->datedebutEvent = $datedebutEvent;

        return $this;
    }

    public function getDatefinEvent(): ?\DateTimeInterface
    {
        return $this->datefinEvent;
    }

    public function setDatefinEvent(\DateTimeInterface $datefinEvent): self
    {
        $this->datefinEvent = $datefinEvent;

        return $this;
    }

    public function getCapaciteEvent(): ?int
    {
        return $this->capaciteEvent;
    }

    public function setCapaciteEvent(int $capaciteEvent): self
    {
        $this->capaciteEvent = $capaciteEvent;

        return $this;
    }

    public function getImageEvent(): ?string
    {
        return $this->imageEvent;
    }

    public function setImageEvent(string $imageEvent): self
    {
        $this->imageEvent = $imageEvent;

        return $this;
    }

    public function getPrixEvent(): ?float
    {
        return $this->prixEvent;
    }

    public function setPrixEvent(float $prixEvent): self
    {
        $this->prixEvent = $prixEvent;

        return $this;
    }

    /**
     * @return Collection<int, Participation>
     */
    public function getParticipations(): Collection
    {
        return $this->participations;
    }

    public function addParticipation(Participation $participation): self
    {
        if (!$this->participations->contains($participation)) {
            $this->participations[] = $participation;
            $participation->setEvenement($this);
        }

        return $this;
    }

    public function removeParticipation(Participation $participation): self
    {
        if ($this->participations->removeElement($participation)) {
            // set the owning side to null (unless already changed)
            if ($participation->getEvenement() === $this) {
                $participation->setEvenement(null);
            }
        }

        return $this;
    }

    public function __toString()
    {
        return $this->nomEvent;
    }
}
