"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group"
import { toast } from "@/components/ui/use-toast"

interface LoginFormProps {
  onLogin: (type: "customer" | "owner") => void
}

export default function LoginForm({ onLogin }: LoginFormProps) {
  const [username, setUsername] = useState("")
  const [password, setPassword] = useState("")
  const [userType, setUserType] = useState<"customer" | "owner">("customer")

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()

    // In a real app, you would validate credentials here
    if (username && password) {
      onLogin(userType)
      toast({
        title: "Login Successful",
        description: `Welcome back, ${username}!`,
      })
    } else {
      toast({
        title: "Login Failed",
        description: "Please enter both username and password.",
        variant: "destructive",
      })
    }
  }

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="space-y-2">
        <Label htmlFor="username">Username</Label>
        <Input
          id="username"
          placeholder="Enter your username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="password">Password</Label>
        <Input
          id="password"
          type="password"
          placeholder="Enter your password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="border-rose-200 focus-visible:ring-rose-500"
        />
      </div>

      <div className="space-y-2">
        <Label>Login As</Label>
        <RadioGroup
          value={userType}
          onValueChange={(value) => setUserType(value as "customer" | "owner")}
          className="flex space-x-4"
        >
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="customer" id="customer" className="text-rose-500" />
            <Label htmlFor="customer">Customer</Label>
          </div>
          <div className="flex items-center space-x-2">
            <RadioGroupItem value="owner" id="owner" className="text-rose-500" />
            <Label htmlFor="owner">Hotel Owner</Label>
          </div>
        </RadioGroup>
      </div>

      <Button type="submit" className="w-full bg-rose-500 hover:bg-rose-600">
        Login
      </Button>
    </form>
  )
}
