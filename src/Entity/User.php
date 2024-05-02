<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * User
 *
 * @ORM\Table(name="user")
 * @ORM\Entity
 */
class User
{
    /**
     * @var int
     *
     * @ORM\Column(name="user_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $userId;

    /**
     * @var string
     *
     * @ORM\Column(name="user_name", type="string", length=255, nullable=false)
     */
    private $userName;

    /**
     * @var string
     *
     * @ORM\Column(name="user_phone", type="string", length=8, nullable=false)
     */
    private $userPhone;

    /**
     * @var string
     *
     * @ORM\Column(name="user_email", type="string", length=255, nullable=false)
     */
    private $userEmail;

    /**
     * @var string
     *
     * @ORM\Column(name="user_password", type="string", length=255, nullable=false)
     */
    private $userPassword;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_birth", type="date", nullable=false)
     */
    private $dateBirth;

    /**
     * @var string
     *
     * @ORM\Column(name="user_sexe", type="string", length=20, nullable=false)
     */
    private $userSexe;

    /**
     * @var string
     *
     * @ORM\Column(name="user_role", type="string", length=20, nullable=false)
     */
    private $userRole;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_creation", type="date", nullable=false)
     */
    private $dateCreation;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="date_last_login", type="date", nullable=false)
     */
    private $dateLastLogin;

    /**
     * @var string
     *
     * @ORM\Column(name="code_check", type="string", length=20, nullable=false)
     */
    private $codeCheck;

    /**
     * @var string
     *
     * @ORM\Column(name="code_principal", type="string", length=255, nullable=false)
     */
    private $codePrincipal;

    /**
     * @var bool
     *
     * @ORM\Column(name="HasDiscount", type="boolean", nullable=false)
     */
    private $hasdiscount;

    public function getUserId(): ?int
    {
        return $this->userId;
    }

    public function getUserName(): ?string
    {
        return $this->userName;
    }

    public function setUserName(string $userName): static
    {
        $this->userName = $userName;

        return $this;
    }

    public function getUserPhone(): ?string
    {
        return $this->userPhone;
    }

    public function setUserPhone(string $userPhone): static
    {
        $this->userPhone = $userPhone;

        return $this;
    }

    public function getUserEmail(): ?string
    {
        return $this->userEmail;
    }

    public function setUserEmail(string $userEmail): static
    {
        $this->userEmail = $userEmail;

        return $this;
    }

    public function getUserPassword(): ?string
    {
        return $this->userPassword;
    }

    public function setUserPassword(string $userPassword): static
    {
        $this->userPassword = $userPassword;

        return $this;
    }

    public function getDateBirth(): ?\DateTimeInterface
    {
        return $this->dateBirth;
    }

    public function setDateBirth(\DateTimeInterface $dateBirth): static
    {
        $this->dateBirth = $dateBirth;

        return $this;
    }

    public function getUserSexe(): ?string
    {
        return $this->userSexe;
    }

    public function setUserSexe(string $userSexe): static
    {
        $this->userSexe = $userSexe;

        return $this;
    }

    public function getUserRole(): ?string
    {
        return $this->userRole;
    }

    public function setUserRole(string $userRole): static
    {
        $this->userRole = $userRole;

        return $this;
    }

    public function getDateCreation(): ?\DateTimeInterface
    {
        return $this->dateCreation;
    }

    public function setDateCreation(\DateTimeInterface $dateCreation): static
    {
        $this->dateCreation = $dateCreation;

        return $this;
    }

    public function getDateLastLogin(): ?\DateTimeInterface
    {
        return $this->dateLastLogin;
    }

    public function setDateLastLogin(\DateTimeInterface $dateLastLogin): static
    {
        $this->dateLastLogin = $dateLastLogin;

        return $this;
    }

    public function getCodeCheck(): ?string
    {
        return $this->codeCheck;
    }

    public function setCodeCheck(string $codeCheck): static
    {
        $this->codeCheck = $codeCheck;

        return $this;
    }

    public function getCodePrincipal(): ?string
    {
        return $this->codePrincipal;
    }

    public function setCodePrincipal(string $codePrincipal): static
    {
        $this->codePrincipal = $codePrincipal;

        return $this;
    }

    public function isHasdiscount(): ?bool
    {
        return $this->hasdiscount;
    }

    public function setHasdiscount(bool $hasdiscount): static
    {
        $this->hasdiscount = $hasdiscount;

        return $this;
    }


}
