### TALLER-2-CORTE-BANCO-DEMO

POO PROGRAMACION ORIENTADA A OBJETOS - SISTEMA BANCARIO

Sistema de Gestión Bancaria (SGB)

---
Asignatura

Programacion II

---
Objetivo de Aprendizaje

Desarrollar una aplicación en Java que integre conceptos de Programación Orientada a Objetos como clases abstractas, interfaces, excepciones, herencia, polimorfismo y encapsulamiento.

---

 Descripción del Problema

Un banco necesita digitalizar su sistema de gestión de clientes, cuentas, empleados y transacciones. El sistema debe permitir el manejo organizado de la información, garantizando control, seguridad y validación de datos.

---

 Funcionalidades Implementadas

 Gestión de Clientes

* Registro de clientes naturales y empresariales
* Asociación de hasta 5 cuentas por cliente
* Consulta de información

 Gestión de Cuentas

* Creación de cuentas: Ahorros, Corriente y Crédito
* Cálculo de intereses según el tipo de cuenta
* Bloqueo y desbloqueo de cuentas

 Gestión de Transacciones

* Depósitos
* Retiros
* Transferencias
* Validación de saldo y estado de cuenta

 Gestión de Empleados

* Cajeros, asesores financieros y gerentes
* Cálculo de salarios con bonificaciones

---

 Historias de Usuario

Se implementaron las historias de usuario solicitadas en el enunciado, cumpliendo con los criterios de aceptación definidos para cada una.

---

 Diseño UML

 Diagrama de Casos de Uso

Incluye los actores principales del sistema:

* Cajero
* Asesor Financiero
* Gerente de Sucursal
* Cliente
* Sistema Auditor

Representa las acciones principales como registro de clientes, apertura de cuentas, gestión de transacciones y consultas.

 Diagrama de Clases

Incluye:

* Jerarquía de herencia completa
* Interfaces implementadas
* Clases abstractas
* Clases concretas
* Relaciones de asociación, composición y dependencia
* Uso de enums y excepciones

---

 Explicación Técnica

El sistema fue desarrollado aplicando los principios de la Programación Orientada a Objetos:

 Encapsulamiento

Uso de atributos privados con validaciones en los setters para garantizar la integridad de los datos.

 Herencia

Se implementa una jerarquía estructurada:

* Persona

  * Empleado

    * Cajero
    * AsesorFinanciero
    * GerenteSucursal
  * Cliente

    * ClienteNatural
    * ClienteEmpresarial

* Cuenta

  * CuentaAhorros
  * CuentaCorriente
  * CuentaCredito

 Polimorfismo

Uso de sobrescritura de métodos como calcularSalario() y calcularInteres(), permitiendo comportamientos distintos según la clase.

 Abstracción

Uso de clases abstractas como Persona, Empleado y Cuenta para definir comportamientos generales.

---

 Interfaces Implementadas

* Consultable
* Transaccionable
* Auditable
* Notificable

Cada interfaz define contratos que son implementados por las clases correspondientes.

---

 Manejo de Excepciones

Se implementó una jerarquía de excepciones personalizadas:

 Checked

* SistemaBancarioException
* SaldoInsuficienteException
* CuentaBloqueadaException
* ClienteNoEncontradoException
* CapacidadExcedidaException

 Unchecked

* BancoRuntimeException
* DatoInvalidoException
* EstadoTransaccionInvalidoException
* PermisoInsuficienteException

---

 Estructura del Proyecto

* modelo.abstractas
* modelo.personas
* modelo.empleados
* modelo.cuentas
* modelo.banco
* modelo.interfaces
* modelo.excepciones
* modelo.enums
* principal

---

 Escenarios Demostrados

Se implementaron los escenarios solicitados en la clase principal, incluyendo:

* Registro de clientes
* Apertura de cuentas
* Depósitos y retiros
* Transferencias
* Manejo de excepciones
* Uso de polimorfismo

---

 Restricciones Técnicas

* Lenguaje: Java 17
* Uso de LocalDate y LocalDateTime
* Uso de arrays estáticos
* Sin uso de colecciones dinámicas
* Sin frameworks externos

---

 Conclusión

El sistema cumple con todos los requisitos del enunciado, aplicando correctamente los conceptos de Programación Orientada a Objetos y demostrando una arquitectura clara, organizada y mantenible.

---

## Autores

* Luis Cantillo Torres
* Elías Castilla
