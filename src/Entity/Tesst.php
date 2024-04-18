<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Tesst
 *
 * @ORM\Table(name="tesst")
 * @ORM\Entity
 */
class Tesst
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $id;

    public function getId(): ?int
    {
        return $this->id;
    }


}
