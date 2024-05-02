<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * ReservationMusee
 *
 * @ORM\Table(name="reservation_musee", indexes={@ORM\Index(name="fk_user_reservation", columns={"id_user"}), @ORM\Index(name="fk_musee_reservation", columns={"id_musee"})})
 * @ORM\Entity
 */
class ReservationMusee
{
    /**
     * @var int
     *
     * @ORM\Column(name="reservation_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $reservationId;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_reservation", type="date", nullable=false)
     */
    private $dateReservation;

    /**
     * @var int
     *
     * @ORM\Column(name="nbr_tickets_reserves", type="integer", nullable=false)
     */
    private $nbrTicketsReserves;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="user_id")
     * })
     */
    private $idUser;

    /**
     * @var \Musee
     *
     * @ORM\ManyToOne(targetEntity="Musee")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_musee", referencedColumnName="id_musee")
     * })
     */
    private $idMusee;

    public function getReservationId(): ?int
    {
        return $this->reservationId;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->dateReservation;
    }

    public function setDateReservation(\DateTimeInterface $dateReservation): static
    {
        $this->dateReservation = $dateReservation;

        return $this;
    }

    public function getNbrTicketsReserves(): ?int
    {
        return $this->nbrTicketsReserves;
    }

    public function setNbrTicketsReserves(int $nbrTicketsReserves): static
    {
        $this->nbrTicketsReserves = $nbrTicketsReserves;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): static
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getIdMusee(): ?Musee
    {
        return $this->idMusee;
    }

    public function setIdMusee(?Musee $idMusee): static
    {
        $this->idMusee = $idMusee;

        return $this;
    }


}
