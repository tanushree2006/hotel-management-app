"use client"

import { useState } from "react"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Card, CardContent } from "@/components/ui/card"
import LoginForm from "./login-form"
import RegistrationForm from "./registration-form"
import CustomerDashboard from "./customer-dashboard"
import OwnerDashboard from "./owner-dashboard"
import { Hotel, BedDouble, UserRound } from "lucide-react"

export default function HotelDashboard() {
  const [loggedIn, setLoggedIn] = useState(false)
  const [userType, setUserType] = useState<"customer" | "owner" | null>(null)

  const handleLogin = (type: "customer" | "owner") => {
    setLoggedIn(true)
    setUserType(type)
  }

  const handleLogout = () => {
    setLoggedIn(false)
    setUserType(null)
  }

  if (loggedIn && userType) {
    return userType === "customer" ? (
      <CustomerDashboard onLogout={handleLogout} />
    ) : (
      <OwnerDashboard onLogout={handleLogout} />
    )
  }

  return (
    <div className="container mx-auto py-10 px-4">
      <div className="flex flex-col items-center justify-center min-h-[80vh]">
        <div className="flex items-center mb-8 gap-3">
          <Hotel className="h-10 w-10 text-rose-600" />
          <h1 className="text-3xl font-bold text-rose-600">MiraVelle</h1>
        </div>

        <Card className="w-full max-w-md shadow-lg border-rose-200">
          <CardContent className="pt-6">
            <Tabs defaultValue="login" className="w-full">
              <TabsList className="grid w-full grid-cols-2 mb-4">
                <TabsTrigger value="login" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
                  <UserRound className="mr-2 h-4 w-4" />
                  Login
                </TabsTrigger>
                <TabsTrigger
                  value="register"
                  className="data-[state=active]:bg-rose-500 data-[state=active]:text-white"
                >
                  <BedDouble className="mr-2 h-4 w-4" />
                  Register
                </TabsTrigger>
              </TabsList>
              <TabsContent value="login">
                <LoginForm onLogin={handleLogin} />
              </TabsContent>
              <TabsContent value="register">
                <RegistrationForm />
              </TabsContent>
            </Tabs>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}
