package com.ForGamers.Model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;

@Entity
@DiscriminatorValue("MANAGER")
@Schema(description = "Clase que representa a los gestores.")
public class Manager extends User {}