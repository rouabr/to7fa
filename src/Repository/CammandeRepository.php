<?php

namespace App\Repository;

use App\Entity\Cammande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Cammande>
 *
 * @method Cammande|null find($id, $lockMode = null, $lockVersion = null)
 * @method Cammande|null findOneBy(array $criteria, array $orderBy = null)
 * @method Cammande[]    findAll()
 * @method Cammande[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class CammandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Cammande::class);
    }


   
}
