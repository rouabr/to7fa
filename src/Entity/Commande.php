<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Commande
 *
 * @ORM\Table(name="commande", indexes={@ORM\Index(name="fk_produit_commande", columns={"id_oeuvre"})})
 * @ORM\Entity
 */
class Commande
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_commande", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idCommande;

    /**
     * @var string
     *
     * @ORM\Column(name="name_commande", type="string", length=255, nullable=false)
     */
    private $nameCommande;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_commande", type="date", nullable=false)
     */
    private $dateCommande;

    /**
     * @var float
     *
     * @ORM\Column(name="prix_commande", type="float", precision=10, scale=0, nullable=false)
     */
    private $prixCommande;

    /**
     * @var string
     *
     * @ORM\Column(name="paiement", type="string", length=20, nullable=false)
     */
    private $paiement;

    /**
     * @var \Oeuvre
     *
     * @ORM\ManyToOne(targetEntity="Oeuvre")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="id_oeuvre", referencedColumnName="id_oeuvre")
     * })
     */
    private $idOeuvre;

    public function getIdCommande(): ?int
    {
        return $this->idCommande;
    }

    public function getNameCommande(): ?string
    {
        return $this->nameCommande;
    }

    public function setNameCommande(string $nameCommande): self
    {
        $this->nameCommande = $nameCommande;

        return $this;
    }

    public function getDateCommande(): ?\DateTimeInterface
    {
        return $this->dateCommande;
    }

    public function setDateCommande(\DateTimeInterface $dateCommande): self
    {
        $this->dateCommande = $dateCommande;

        return $this;
    }

    public function getPrixCommande(): ?float
    {
        return $this->prixCommande;
    }

    public function setPrixCommande(float $prixCommande): self
    {
        $this->prixCommande = $prixCommande;

        return $this;
    }

    public function getPaiement(): ?string
    {
        return $this->paiement;
    }

    public function setPaiement(string $paiement): self
    {
        $this->paiement = $paiement;

        return $this;
    }

    public function getIdOeuvre(): ?Oeuvre
    {
        return $this->idOeuvre;
    }

    public function setIdOeuvre(?Oeuvre $idOeuvre): self
    {
        $this->idOeuvre = $idOeuvre;

        return $this;
    }


}
