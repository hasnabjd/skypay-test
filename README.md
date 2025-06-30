# Système de Réservation Hôtel - Diagrammes de Classe

## Description

Ce readme contient le diagrammes de classe pour un système de gestion de réservation hôtelière complet. Il modélise toutes les entités principales, leurs attributs, méthodes et les relations entre elles.


![img.png](src%2Fmain%2Fresources%2Fimg.png)
## Design Questions (Bonus)

### 1. **Suppose we put all the functions inside the same service. Is this the recommended approach? Please explain.**

No, putting all the functions inside the same service is not the recommended approach. This leads to a violation of the Single Responsibility Principle of SOLID principles, which states that a class or service should have only one reason to change. When all logic is centralized in a single service, the code becomes harder to maintain, test, and extend. It increases coupling and reduces cohesion, making the system less modular and more prone to bugs. Instead, it is better to separate concerns by creating dedicated services for different functionalities (e.g., BookingService, RoomService, UserService), which improves maintainability, scalability, and testability.




### 2. **In this design, we chose to have a function setRoom(..) that should not impact the previous bookings. What is another way? What is your recommendation? Please explain and justify.**

Another way to handle room changes without impacting previous bookings is to use an immutable booking record. Instead of modifying the existing booking, you can create a new booking with the updated room and mark the old booking as changed or cancelled. This approach preserves the history of all bookings and ensures data integrity. My recommendation is to avoid mutating critical booking data directly, especially if it affects historical records. Maintaining an audit trail by creating new records for changes is a best practice in systems where historical accuracy and traceability are important, such as hotel reservation systems.    

## Test

### Compile and Test
```bash
mvn clean compile test
```
### Run Tests
```bash
mvn test
```

