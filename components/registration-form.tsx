"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { toast } from "@/components/ui/use-toast"

export default function RegistrationForm() {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    confirmPassword: "",
    fullName: "",
    email: "",
    phone: "",
    userType: "customer" as "customer" | "owner",
  })

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setFormData((prev) => ({ ...prev, [name]: value }))
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()

    // Validate passwords match
    if (formData.password !== formData.confirmPassword) {
      toast({
        title: "Registration Failed",
        description: "Passwords do not match.",
        variant: "destructive",
      })
      return
    }

    // In a real app, you would send this data to your backend
    toast({
      title: "Registration Successful",
      description: `Account created for ${formData.fullName}. You can now login.`,
    })

    // Reset form
    setFormData({
      username: "",
      password: "",
      confirmPassword: "",
      fullName: "",
      email: "",
      phone: "",
      userType: "customer",
    })
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="space-y-2">
        <Label htmlFor="fullName">Full Name</Label>
        <Input
          id="fullName"
          name="fullName"
          placeholder="Enter your full name"
          value={formData.fullName}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="email">Email</Label>
        <Input
          id="email"
          name="email"
          type="email"
          placeholder="Enter your email"
          value={formData.email}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="phone">Phone Number</Label>
        <Input
          id="phone"
          name="phone"
          placeholder="Enter your phone number"
          value={formData.phone}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="username">Username</Label>
        <Input
          id="username"
          name="username"
          placeholder="Choose a username"
          value={formData.username}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="password">Password</Label>
        <Input
          id="password"
          name="password"
          type="password"
          placeholder="Choose a password"
          value={formData.password}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="confirmPassword">Confirm Password</Label>
        <Input
          id="confirmPassword"
          name="confirmPassword"
          type="password"
          placeholder="Confirm your password"
          value={formData.confirmPassword}
          onChange={handleChange}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label>Register As</Label>
        <RadioGroup
          value={formData.userType}
          onValueChange={(value) => setFormData((prev) => ({ ...prev, userType: value as "customer" | "owner" }))}
          className="flex space-x-4"
        >
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="customer" id="reg-customer" className="text-rose-500" />
            <Label htmlFor="reg-customer">Customer</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="owner" id="reg-owner" className="text-rose-500" />
            <Label htmlFor="reg-owner">Hotel Owner</Label>
          </div>
        </RadioGroup>
      </div>

      <Button type="submit" className="w-full bg-rose-500 hover:bg-rose-600">
        Register
      </Button>
    </form>
  )
}
