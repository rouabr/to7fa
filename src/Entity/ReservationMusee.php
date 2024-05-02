<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * ReservationMusee
 *
 * @ORM\Table(name="reservation_musee", indexes={@ORM\Index(name="fk_musee_reservation", columns={"id_musee"}), @ORM\Index(name="fk_user_reservation", columns={"id_user"})})
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
     * @var \Musee
     *
     * @ORM\ManyToOne(targetEntity="Musee")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_musee", referencedColumnName="id_musee")
     * })
     */
    private $idMusee;

    /**
     * @var \User
     *
     * @ORM\ManyToOne(targetEntity="User")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_user", referencedColumnName="user_id")
     * })
     */
    private $idUser;

    public function getReservationId(): ?int
    {
        return $this->reservationId;
    }

    public function getDateReservation(): ?\DateTimeInterface
    {
        return $this->dateReservation;
    }

    public function setDateReservation(\DateTimeInterface $dateReservation): self
    {
        $this->dateReservation = $dateReservation;

        return $this;
    }

    public function getNbrTicketsReserves(): ?int
    {
        return $this->nbrTicketsReserves;
    }

    public function setNbrTicketsReserves(int $nbrTicketsReserves): self
    {
        $this->nbrTicketsReserves = $nbrTicketsReserves;

        return $this;
    }

    public function getIdMusee(): ?Musee
    {
        return $this->idMusee;
    }

    public function setIdMusee(?Musee $idMusee): self
    {
        $this->idMusee = $idMusee;

        return $this;
    }

    public function getIdUser(): ?User
    {
        return $this->idUser;
    }

    public function setIdUser(?User $idUser): self
    {
        $this->idUser = $idUser;

        return $this;
    }


}
