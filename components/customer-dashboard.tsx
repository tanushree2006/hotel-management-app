"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { BedDouble, Coffee, LogOut, Search, Star, User, Utensils } from "lucide-react"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { toast } from "@/components/ui/use-toast"

interface CustomerDashboardProps {
  onLogout: () => void
}

export default function CustomerDashboard({ onLogout }: CustomerDashboardProps) {
  const [activeBookings, setActiveBookings] = useState([
    {
      id: "B001",
      roomNumber: "101",
      roomType: "Deluxe",
      checkIn: "2023-04-20",
      checkOut: "2023-04-25",
      status: "Confirmed",
      price: "$750",
    },
    {
      id: "B002",
      roomNumber: "205",
      roomType: "Suite",
      checkIn: "2023-05-15",
      checkOut: "2023-05-20",
      status: "Pending",
      price: "$1200",
    },
  ])

  const availableRooms = [
    {
      id: "R101",
      roomNumber: "301",
      roomType: "Standard",
      price: "$120/night",
      amenities: ["Wi-Fi", "TV", "Air Conditioning"],
      image: "/placeholder.svg?height=100&width=200",
    },
    {
      id: "R102",
      roomNumber: "302",
      roomType: "Deluxe",
      price: "$180/night",
      amenities: ["Wi-Fi", "TV", "Air Conditioning", "Mini Bar", "Ocean View"],
      image: "/placeholder.svg?height=100&width=200",
    },
    {
      id: "R103",
      roomNumber: "401",
      roomType: "Suite",
      price: "$250/night",
      amenities: ["Wi-Fi", "TV", "Air Conditioning", "Mini Bar", "Ocean View", "Jacuzzi"],
      image: "/placeholder.svg?height=100&width=200",
    },
  ]

  const handleBookRoom = (roomId: string) => {
    toast({
      title: "Room Booked",
      description: `You have successfully booked room ${roomId}. Check your bookings tab for details.`,
    })
  }

  const handleCancelBooking = (bookingId: string) => {
    setActiveBookings(activeBookings.filter((booking) => booking.id !== bookingId))
    toast({
      title: "Booking Cancelled",
      description: `Booking ${bookingId} has been cancelled.`,
    })
  }

  const handleOrderRoomService = () => {
    toast({
      title: "Room Service Ordered",
      description: "Your room service order has been placed. It will arrive in approximately 30 minutes.",
    })
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-rose-50 to-indigo-50">
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-3">
            <BedDouble className="h-8 w-8 text-rose-600" />
            <h1 className="text-2xl font-bold text-rose-600">MiraVelle</h1>
          </div>

          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <Avatar>
                <AvatarImage src="/placeholder.svg?height=40&width=40" alt="User" />
                <AvatarFallback className="bg-rose-200 text-rose-700">
                  <User className="h-5 w-5" />
                </AvatarFallback>
              </Avatar>
              <div>
                <p className="text-sm font-medium">John Doe</p>
                <p className="text-xs text-gray-500">Customer</p>
              </div>
            </div>

            <Button
              variant="outline"
              size="sm"
              onClick={onLogout}
              className="border-rose-200 text-rose-600 hover:bg-rose-50"
            >
              <LogOut className="h-4 w-4 mr-2" />
              Logout
            </Button>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        <Tabs defaultValue="bookings" className="w-full">
          <TabsList className="grid w-full grid-cols-3 mb-8">
            <TabsTrigger value="bookings" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <BedDouble className="mr-2 h-4 w-4" />
              My Bookings
            </TabsTrigger>
            <TabsTrigger value="rooms" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <Search className="mr-2 h-4 w-4" />
              Find Rooms
            </TabsTrigger>
            <TabsTrigger value="services" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <Utensils className="mr-2 h-4 w-4" />
              Room Services
            </TabsTrigger>
          </TabsList>

          <TabsContent value="bookings">
            <div className="grid gap-6">
              <h2 className="text-2xl font-bold text-gray-800">My Bookings</h2>

              {activeBookings.length > 0 ? (
                <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                  {activeBookings.map((booking) => (
                    <Card key={booking.id} className="border-rose-200">
                      <CardHeader className="pb-2">
                        <div className="flex justify-between items-start">
                          <div>
                            <CardTitle>Room {booking.roomNumber}</CardTitle>
                            <CardDescription>{booking.roomType} Room</CardDescription>
                          </div>
                          <Badge className={booking.status === "Confirmed" ? "bg-green-500" : "bg-amber-500"}>
                            {booking.status}
                          </Badge>
                        </div>
                      </CardHeader>
                      <CardContent>
                        <div className="grid gap-2">
                          <div className="flex justify-between text-sm">
                            <span className="text-gray-500">Booking ID:</span>
                            <span className="font-medium">{booking.id}</span>
                          </div>
                          <div className="flex justify-between text-sm">
                            <span className="text-gray-500">Check-in:</span>
                            <span className="font-medium">{booking.checkIn}</span>
                          </div>
                          <div className="flex justify-between text-sm">
                            <span className="text-gray-500">Check-out:</span>
                            <span className="font-medium">{booking.checkOut}</span>
                          </div>
                          <div className="flex justify-between text-sm">
                            <span className="text-gray-500">Total Price:</span>
                            <span className="font-medium">{booking.price}</span>
                          </div>
                        </div>
                      </CardContent>
                      <CardFooter>
                        <Button
                          variant="outline"
                          className="w-full border-rose-200 text-rose-600 hover:bg-rose-50"
                          onClick={() => handleCancelBooking(booking.id)}
                        >
                          Cancel Booking
                        </Button>
                      </CardFooter>
                    </Card>
                  ))}
                </div>
              ) : (
                <Card className="border-rose-200">
                  <CardContent className="flex flex-col items-center justify-center py-10">
                    <BedDouble className="h-16 w-16 text-gray-300 mb-4" />
                    <p className="text-gray-500 text-center">You don't have any active bookings.</p>
                    <p className="text-gray-500 text-center">Go to the Find Rooms tab to book a room.</p>
                  </CardContent>
                </Card>
              )}
            </div>
          </TabsContent>

          <TabsContent value="rooms">
            <div className="grid gap-6">
              <h2 className="text-2xl font-bold text-gray-800">Find Rooms</h2>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <div className="grid gap-4 md:grid-cols-4">
                    <div className="space-y-2">
                      <Label htmlFor="check-in">Check-in Date</Label>
                      <Input id="check-in" type="date" className="border-rose-200 focus-visible:ring-rose-500" />
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="check-out">Check-out Date</Label>
                      <Input id="check-out" type="date" className="border-rose-200 focus-visible:ring-rose-500" />
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="room-type">Room Type</Label>
                      <Select>
                        <SelectTrigger id="room-type" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="Any" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="any">Any</SelectItem>
                          <SelectItem value="standard">Standard</SelectItem>
                          <SelectItem value="deluxe">Deluxe</SelectItem>
                          <SelectItem value="suite">Suite</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="flex items-end">
                      <Button className="w-full bg-rose-500 hover:bg-rose-600">
                        <Search className="mr-2 h-4 w-4" />
                        Search
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>

              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {availableRooms.map((room) => (
                  <Card key={room.id} className="border-rose-200 overflow-hidden">
                    <img
                      src={room.image || "/placeholder.svg"}
                      alt={`Room ${room.roomNumber}`}
                      className="w-full h-48 object-cover"
                    />
                    <CardHeader className="pb-2">
                      <div className="flex justify-between items-start">
                        <div>
                          <CardTitle>Room {room.roomNumber}</CardTitle>
                          <CardDescription>{room.roomType} Room</CardDescription>
                        </div>
                        <div className="flex">
                          {[1, 2, 3, 4, 5].map((star) => (
                            <Star key={star} className="h-4 w-4 fill-amber-400 text-amber-400" />
                          ))}
                        </div>
                      </div>
                    </CardHeader>
                    <CardContent>
                      <p className="font-bold text-lg text-rose-600 mb-2">{room.price}</p>
                      <div className="flex flex-wrap gap-1 mb-2">
                        {room.amenities.map((amenity, index) => (
                          <Badge key={index} variant="outline" className="border-rose-200 text-rose-600">
                            {amenity}
                          </Badge>
                        ))}
                      </div>
                    </CardContent>
                    <CardFooter>
                      <Button
                        className="w-full bg-rose-500 hover:bg-rose-600"
                        onClick={() => handleBookRoom(room.roomNumber)}
                      >
                        Book Now
                      </Button>
                    </CardFooter>
                  </Card>
                ))}
              </div>
            </div>
          </TabsContent>

          <TabsContent value="services">
            <div className="grid gap-6">
              <h2 className="text-2xl font-bold text-gray-800">Room Services</h2>

              <div className="grid gap-6 md:grid-cols-2">
                <Card className="border-rose-200">
                  <CardHeader>
                    <CardTitle className="flex items-center gap-2">
                      <Coffee className="h-5 w-5 text-rose-600" />
                      Food & Beverages
                    </CardTitle>
                    <CardDescription>Order food and drinks to your room</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="space-y-4">
                      <div className="space-y-2">
                        <Label htmlFor="room-number">Room Number</Label>
                        <Input
                          id="room-number"
                          placeholder="Enter your room number"
                          className="border-rose-200 focus-visible:ring-rose-500"
                        />
                      </div>

                      <div className="space-y-2">
                        <Label htmlFor="order-items">Order Items</Label>
                        <Select>
                          <SelectTrigger id="order-items" className="border-rose-200 focus:ring-rose-500">
                            <SelectValue placeholder="Select items" />
                          </SelectTrigger>
                          <SelectContent>
                            <SelectItem value="breakfast">Breakfast - $15</SelectItem>
                            <SelectItem value="lunch">Lunch - $25</SelectItem>
                            <SelectItem value="dinner">Dinner - $30</SelectItem>
                            <SelectItem value="drinks">Drinks - $10</SelectItem>
                          </SelectContent>
                        </Select>
                      </div>

                      <div className="space-y-2">
                        <Label htmlFor="special-requests">Special Requests</Label>
                        <Input
                          id="special-requests"
                          placeholder="Any special requests?"
                          className="border-rose-200 focus-visible:ring-rose-500"
                        />
                      </div>
                    </div>
                  </CardContent>
                  <CardFooter>
                    <Button className="w-full bg-rose-500 hover:bg-rose-600" onClick={handleOrderRoomService}>
                      Place Order
                    </Button>
                  </CardFooter>
                </Card>

                <Card className="border-rose-200">
                  <CardHeader>
                    <CardTitle className="flex items-center gap-2">
                      <Utensils className="h-5 w-5 text-rose-600" />
                      Other Services
                    </CardTitle>
                    <CardDescription>Request additional services for your stay</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <div className="space-y-4">
                      <div className="space-y-2">
                        <Label htmlFor="service-room">Room Number</Label>
                        <Input
                          id="service-room"
                          placeholder="Enter your room number"
                          className="border-rose-200 focus-visible:ring-rose-500"
                        />
                      </div>

                      <div className="space-y-2">
                        <Label htmlFor="service-type">Service Type</Label>
                        <Select>
                          <SelectTrigger id="service-type" className="border-rose-200 focus:ring-rose-500">
                            <SelectValue placeholder="Select service" />
                          </SelectTrigger>
                          <SelectContent>
                            <SelectItem value="cleaning">Room Cleaning</SelectItem>
                            <SelectItem value="laundry">Laundry Service</SelectItem>
                            <SelectItem value="maintenance">Maintenance Request</SelectItem>
                            <SelectItem value="spa">Spa Appointment</SelectItem>
                          </SelectContent>
                        </Select>
                      </div>

                      <div className="space-y-2">
                        <Label htmlFor="service-time">Preferred Time</Label>
                        <Input id="service-time" type="time" className="border-rose-200 focus-visible:ring-rose-500" />
                      </div>
                    </div>
                  </CardContent>
                  <CardFooter>
                    <Button className="w-full bg-rose-500 hover:bg-rose-600" onClick={handleOrderRoomService}>
                      Request Service
                    </Button>
                  </CardFooter>
                </Card>
              </div>
            </div>
          </TabsContent>
        </Tabs>
      </main>
    </div>
  )
}
