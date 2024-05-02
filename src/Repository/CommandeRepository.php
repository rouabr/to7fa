<?php

namespace App\Repository;

use Doctrine\ORM\EntityRepository;
use App\Entity\Commande;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

class CommandeRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Commande::class);
    }
    public function countSalesthismonth()
    {
        $lastMonth = new \DateTime('last day of this month');
        $currentMonth = new \DateTime('first day of this month');

        return $this->createQueryBuilder('c')
            ->select('SUM(c.prixCommande)')
            ->where('c.dateCommande < :lastMonth')
            ->andWhere('c.dateCommande >= :currentMonth')
            ->setParameter('lastMonth', $lastMonth->format('Y-m-d'))
        ->setParameter('currentMonth', $currentMonth->format('Y-m-d'))
            ->getQuery()
            ->getSingleScalarResult();
    }

    public function countSales()
    {
        $lastMonth = new \DateTime('last day of this month');
        $currentMonth = new \DateTime('first day of this month');

        return $this->createQueryBuilder('c')
            ->select('SUM(c.quantity)')
            ->where('c.dateCommande < :lastMonth')
            ->andWhere('c.dateCommande >= :currentMonth')
            ->setParameter('lastMonth', $lastMonth->format('Y-m-d'))
        ->setParameter('currentMonth', $currentMonth->format('Y-m-d'))
            ->getQuery()
            ->getSingleScalarResult();
    }


     // Custom method to get historical data for the last month
     public function getHistoricalDataForLastMonth()
     {
         $lastMonthStartDate = new \DateTime('first day of last month');
         $lastMonthEndDate = new \DateTime('last day of last month');
 
         return $this->createQueryBuilder('c')
             ->where('c.dateCommande BETWEEN :start_date AND :end_date')
             ->setParameter('start_date', $lastMonthStartDate)
             ->setParameter('end_date', $lastMonthEndDate)
             ->getQuery()
             ->getResult();
     }


     public function findUserPurchasesWithinYear(int $x)
     {
         $entityManager = $this->getEntityManager();
 
         $query = $entityManager->createQuery(
             'SELECT c
             FROM App\Entity\Commande c
             WHERE c.idUser  = :user
             AND c.dateCommande >= :startDate
             AND c.dateCommande <= :endDate'
         )->setParameter('user', $x)
          ->setParameter('startDate', new \DateTime('-1 year'))
          ->setParameter('endDate', new \DateTime());
 
         return $query->getResult();
     }

     public function findByUserId($userId)
    {
        return $this->createQueryBuilder('c')
            ->andWhere('c.idUser = :userId')
            ->setParameter('userId', $userId)
            ->getQuery()
            ->getResult();
    }

}