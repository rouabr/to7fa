<?php 
namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;

/**
 * @Annotation
 */
class TicketsDisponibles extends Constraint
{
    public $message = 'Le nombre de tickets réservés dépasse le nombre de tickets disponibles dans le musée.';
}