<?php
namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;

/**
 * @Annotation
 */
class DateNotInFuture extends Constraint
{
    public $message = 'La date de réservation ne peut pas être inferieur à celle d\'aujourd\'hui.';
}