<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use App\Validator\Constraints as CustomAssert;
use DateTime;

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
 * @var string
 *
 * @ORM\Column(name="date_reservation", type="string", length=255, nullable=false)
 * @Assert\NotBlank(message="La date de réservation ne peut pas être vide")
 * @CustomAssert\DateNotInFuture(message="La date de réservation ne peut pas être postérieure à aujourd'hui")
 */
private $dateReservation;

    /**
     * @var int
     *
     * @ORM\Column(name="nbr_tickets_reserves", type="integer", nullable=false)
     * * @CustomAssert\TicketsDisponibles()
     * @Assert\NotBlank(message="Le nombre de tickets réservés ne peut pas être vide")
     * @Assert\Positive(message="Le nombre de tickets réservés doit être un nombre positif")
     */
    private $nbrTicketsReserves;

    /**
     * @var int|null
     *
     * @ORM\Column(name="id_user", type="integer", nullable=true)
     */
    private $idUser;

    /**
     * @var int|null
     *
     * @ORM\Column(name="id_musee", type="integer", nullable=true)
     * @Assert\NotBlank(message="L'ID du musée ne peut pas être vide")
     * @Assert\Positive(message="L'ID du musée doit être un nombre positif")
     */
    private $idMusee;

    public function getReservationId(): ?int
    {
        return $this->reservationId;
    }

    public function getDateReservation(): ?string
    {
        return $this->dateReservation;
    }

    
    public function setDateReservation($dateReservation): self
    {
        // If the passed value is already a \DateTimeInterface object, convert it to a string in 'Y-m-d' format
        if ($dateReservation instanceof \DateTimeInterface) {
            $this->dateReservation = $dateReservation->format('Y-m-d');
        } else {
            // If the passed value is a string, use it directly
            $this->dateReservation = $dateReservation;
        }
    
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

    public function getIdUser(): ?int
    {
        return $this->idUser;
    }

    public function setIdUser(?int $idUser): static
    {
        $this->idUser = $idUser;

        return $this;
    }

    public function getIdMusee(): ?int
    {
        return $this->idMusee;
    }

    public function setIdMusee(?int $idMusee): static
    {
        $this->idMusee = $idMusee;

        return $this;
    }


}
