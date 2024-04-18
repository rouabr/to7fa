<?php
namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;
use Symfony\Component\Validator\ConstraintValidator;

class DateNotInFutureValidator extends ConstraintValidator
{
    public function validate($value, Constraint $constraint)
    {
        if ($value && strtotime($value) < strtotime('today')) {
            $this->context->buildViolation($constraint->message)
                ->addViolation();
        }
    }
}