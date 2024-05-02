<?php

namespace App\Repository;

use Doctrine\ORM\EntityRepository;
use App\Entity\Panier;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class PanierRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Panier::class);
    }
     
    public function findByTitre($oeuvreName)
    {
        return $this->createQueryBuilder('p')
            ->join('p.idOeuvre', 'o')
            ->where('o.titre = :name')
            ->setParameter('name', $oeuvreName)
            ->getQuery()
            ->getResult();
    }

    public function findAllOrderedByPrice()
    {
        return $this->createQueryBuilder('p')
            ->leftJoin('p.idOeuvre', 'o')
            ->orderBy('o.prix', 'ASC')
            ->getQuery()
            ->getResult();
    }

}