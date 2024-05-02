<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Musee
 *
 * @ORM\Table(name="musee", indexes={@ORM\Index(name="user", columns={"user"})})
 * @ORM\Entity
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
     */
    private $nomMusee;

    /**
     * @var string
     *
     * @ORM\Column(name="adresse", type="string", length=255, nullable=false)
     */
    private $adresse;

    /**
     * @var string
     *
     * @ORM\Column(name="ville", type="string", length=255, nullable=false)
     */
    private $ville;

    /**
     * @var int
     *
     * @ORM\Column(name="nbr_tickets_disponibles", type="integer", nullable=false)
     */
    private $nbrTicketsDisponibles;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255, nullable=false)
     */
    private $description;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="user", referencedColumnName="user_id")
     * })
     */
    private $user;

    public function getIdMusee(): ?int
    {
        return $this->idMusee;
    }

    public function getNomMusee(): ?string
    {
        return $this->nomMusee;
    }

    public function setNomMusee(string $nomMusee): self
    {
        $this->nomMusee = $nomMusee;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getVille(): ?string
    {
        return $this->ville;
    }

    public function setVille(string $ville): self
    {
        $this->ville = $ville;

        return $this;
    }

    public function getNbrTicketsDisponibles(): ?int
    {
        return $this->nbrTicketsDisponibles;
    }

    public function setNbrTicketsDisponibles(int $nbrTicketsDisponibles): self
    {
        $this->nbrTicketsDisponibles = $nbrTicketsDisponibles;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }


}
