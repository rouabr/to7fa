<?php

namespace App\Repository;

use Doctrine\ORM\EntityRepository;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;
use App\Entity\Oeuvre;

class OeuvreRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Oeuvre::class);
    }
}