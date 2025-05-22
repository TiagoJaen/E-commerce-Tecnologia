package com.ForGamers.Model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
@Schema(description = "Clase que representa a los admins.")
public class Admin extends User {}