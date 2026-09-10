package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testValidViolation() throws Exception {

        Violation violation =
                new Violation(
                        "TN01AB1234",
                        "Ravi",
                        "Speeding",
                        1000.0,
                        "Vellore");

        assertEquals(
                "TN01AB1234",
                violation.getVehicleNumber());

        assertEquals(
                "Unpaid",
                violation.getStatus());
    }

    @Test
    public void testTotalUnpaidFine() throws Exception {

        App system = new App();

        system.addViolation(
                new Violation(
                        "TN01AA1111",
                        "A",
                        "Speeding",
                        1000.0,
                        "Vellore"));

        system.addViolation(
                new Violation(
                        "TN01BB2222",
                        "B",
                        "Signal Jump",
                        500.0,
                        "Katpadi"));

        assertEquals(
                1500.0,
                system.calculateTotalFine(),
                0.001);
    }

    @Test
    public void testFinePayment() throws Exception {

        App system = new App();

        Violation violation =
                new Violation(
                        "TN01CC3333",
                        "C",
                        "No Helmet",
                        500.0,
                        "Vellore");

        system.addViolation(violation);

        system.payFine("TN01CC3333");

        assertEquals(
                "Paid",
                violation.getStatus());

        assertEquals(
                0.0,
                system.calculateTotalFine(),
                0.001);
    }

    @Test
    public void testSortByFineDescending()
            throws Exception {

        App system = new App();

        system.addViolation(
                new Violation(
                        "A",
                        "A",
                        "Speeding",
                        500.0,
                        "Vellore"));

        system.addViolation(
                new Violation(
                        "B",
                        "B",
                        "Dangerous Driving",
                        2000.0,
                        "Vellore"));

        assertEquals(
                "B",
                system.sortByFineDescending()
                        .get(0)
                        .getVehicleNumber());
    }

    @Test
    public void testLateFee() throws Exception {

        assertEquals(
                100.0,
                App.calculateLateFee(
                        1000.0,
                        5),
                0.001);
    }

    @Test
    public void testMarkPaid() throws Exception {

        Violation violation =
                new Violation(
                        "TN01DD4444",
                        "D",
                        "Parking",
                        300.0,
                        "Vellore");

        violation.markPaid();

        assertEquals(
                "Paid",
                violation.getStatus());
    }

    @Test(expected = InvalidViolationException.class)
    public void testEmptyVehicleNumber()
            throws Exception {

        new Violation(
                "",
                "Ravi",
                "Speeding",
                1000.0,
                "Vellore");
    }

    @Test(expected = InvalidViolationException.class)
    public void testNegativeFine()
            throws Exception {

        new Violation(
                "TN01EE5555",
                "E",
                "Speeding",
                -100.0,
                "Vellore");
    }

    @Test
    public void testInvalidPayment()
            throws Exception {

        App system = new App();

        system.addViolation(
                new Violation(
                        "TN01FF6666",
                        "F",
                        "Speeding",
                        1000.0,
                        "Vellore"));

        try {

            system.payFine("TN01XX9999");

            fail("Expected PaymentException");

        } catch (PaymentException expected) {
            // Expected exception
        }
    }
}
