<?php
namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;
use Symfony\Component\Validator\ConstraintValidator;
use Doctrine\ORM\EntityManagerInterface;
use App\Entity\Musee;
use App\Entity\ReservationMusee;


class TicketsDisponiblesValidator extends ConstraintValidator
{
    private $entityManager;

    public function __construct(EntityManagerInterface $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    public function validate($value, Constraint $constraint)
    {
        $reservationMusee = $this->context->getObject();
        $museeRepository = $this->entityManager->getRepository(Musee::class);
        $musee = $museeRepository->find($reservationMusee->getIdMusee());

        if (!$musee) {
            return;
        }

        if ($value > $musee->getNbrTicketsDisponibles()) {
            $this->context->buildViolation($constraint->message)
                ->setParameter('{{ value }}', $value)
                ->addViolation();
        }
    }
}