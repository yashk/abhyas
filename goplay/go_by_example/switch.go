package main

import (
	"fmt"
	"time"
)

func main() {

	// basic switch case
	i := 2
	fmt.Print("Write ", i , " as ")
	switch i {
	case 1:
		fmt.Println("one")
	case 2:
		fmt.Println("two")
	case 3:
		fmt.Println("three")
	}

	fmt.Println(time.Now().Weekday())

	switch time.Now().Weekday() {
	case time.Saturday, time.Sunday: // use comma to separate multiple expressions
		fmt.Println("its a weekend")
	default: // default
		fmt.Println("its a weekday")
	}
	// type switch
	whatAmI := func(i interface{}) {
		switch t := i.(type) {
		case bool:
			fmt.Println("I am bool")
		case int:
			fmt.Println("I am an int")
		default:
			fmt.Printf("Don't know type %T\n", t)
		}
	}

	whatAmI(true)
	whatAmI(1)
	whatAmI("hey")
}
