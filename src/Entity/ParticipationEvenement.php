<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ParticipationEvenement
 *
 * @ORM\Table(name="participation_evenement", indexes={@ORM\Index(name="fk_event_participation", columns={"id_event"})})
 * @ORM\Entity
 */
class ParticipationEvenement
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_participation", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idParticipation;

    /**
     * @var int|null
     *
     * @ORM\Column(name="nombre_participation", type="integer", nullable=true)
     */
    private $nombreParticipation;

    /**
     * @var string|null
     *
     * @ORM\Column(name="num_tel", type="string", length=255, nullable=true)
     */
    private $numTel;

    

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_event", referencedColumnName="id_event")
     * })
     */
    private $idEvent;

    public function getIdParticipation(): ?int
    {
        return $this->idParticipation;
    }

    public function getNombreParticipation(): ?int
    {
        return $this->nombreParticipation;
    }

    public function setNombreParticipation(?int $nombreParticipation): self
    {
        $this->nombreParticipation = $nombreParticipation;

        return $this;
    }

    public function getNumTel(): ?string
    {
        return $this->numTel;
    }

    public function setNumTel(?string $numTel): self
    {
        $this->numTel = $numTel;

        return $this;
    }

    /* public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    } */

    public function getIdEvent(): ?Evenement
    {
        return $this->idEvent;
    }

    public function setIdEvent(?Evenement $idEvent): self
    {
        $this->idEvent = $idEvent;

        return $this;
    }


}
