"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { BedDouble, Building, LogOut, Plus, User, Users } from "lucide-react"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table"
import { toast } from "@/components/ui/use-toast"

interface OwnerDashboardProps {
  onLogout: () => void
}

export default function OwnerDashboard({ onLogout }: OwnerDashboardProps) {
  const [rooms, setRooms] = useState([
    {
      id: "R101",
      roomNumber: "101",
      roomType: "Standard",
      status: "Available",
      price: "$120/night",
      lastCleaned: "2023-04-21",
    },
    {
      id: "R102",
      roomNumber: "102",
      roomType: "Deluxe",
      status: "Occupied",
      price: "$180/night",
      lastCleaned: "2023-04-20",
    },
    {
      id: "R103",
      roomNumber: "201",
      roomType: "Suite",
      status: "Maintenance",
      price: "$250/night",
      lastCleaned: "2023-04-19",
    },
    {
      id: "R104",
      roomNumber: "202",
      roomType: "Standard",
      status: "Available",
      price: "$120/night",
      lastCleaned: "2023-04-21",
    },
    {
      id: "R105",
      roomNumber: "301",
      roomType: "Deluxe",
      status: "Occupied",
      price: "$180/night",
      lastCleaned: "2023-04-20",
    },
  ])

  const [bookings, setBookings] = useState([
    {
      id: "B001",
      customerName: "John Smith",
      roomNumber: "102",
      checkIn: "2023-04-18",
      checkOut: "2023-04-23",
      status: "Active",
      totalAmount: "$900",
    },
    {
      id: "B002",
      customerName: "Jane Doe",
      roomNumber: "301",
      checkIn: "2023-04-20",
      checkOut: "2023-04-25",
      status: "Active",
      totalAmount: "$900",
    },
    {
      id: "B003",
      customerName: "Robert Johnson",
      roomNumber: "201",
      checkIn: "2023-04-15",
      checkOut: "2023-04-19",
      status: "Completed",
      totalAmount: "$1000",
    },
  ])

  const [staff, setStaff] = useState([
    {
      id: "S001",
      name: "Michael Brown",
      position: "Receptionist",
      contact: "555-1234",
      status: "On Duty",
    },
    {
      id: "S002",
      name: "Sarah Wilson",
      position: "Housekeeper",
      contact: "555-5678",
      status: "On Duty",
    },
    {
      id: "S003",
      name: "David Lee",
      position: "Maintenance",
      contact: "555-9012",
      status: "Off Duty",
    },
  ])

  const handleAddRoom = () => {
    toast({
      title: "Room Added",
      description: "New room has been added successfully.",
    })
  }

  const handleUpdateRoomStatus = (roomId: string, newStatus: string) => {
    setRooms(rooms.map((room) => (room.id === roomId ? { ...room, status: newStatus } : room)))
    toast({
      title: "Room Status Updated",
      description: `Room ${roomId} status changed to ${newStatus}.`,
    })
  }

  const handleCheckoutGuest = (bookingId: string) => {
    setBookings(bookings.map((booking) => (booking.id === bookingId ? { ...booking, status: "Completed" } : booking)))
    toast({
      title: "Guest Checked Out",
      description: `Booking ${bookingId} has been marked as completed.`,
    })
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-rose-50 to-indigo-50">
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="flex items-center gap-3">
            <Building className="h-8 w-8 text-rose-600" />
            <h1 className="text-2xl font-bold text-rose-600">Hotel Management</h1>
          </div>

          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <Avatar>
                <AvatarImage src="/placeholder.svg?height=40&width=40" alt="Admin" />
                <AvatarFallback className="bg-rose-200 text-rose-700">
                  <User className="h-5 w-5" />
                </AvatarFallback>
              </Avatar>
              <div>
                <p className="text-sm font-medium">Admin User</p>
                <p className="text-xs text-gray-500">Hotel Owner</p>
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
        <Tabs defaultValue="rooms" className="w-full">
          <TabsList className="grid w-full grid-cols-3 mb-8">
            <TabsTrigger value="rooms" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <BedDouble className="mr-2 h-4 w-4" />
              Rooms Management
            </TabsTrigger>
            <TabsTrigger value="bookings" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <Users className="mr-2 h-4 w-4" />
              Bookings
            </TabsTrigger>
            <TabsTrigger value="staff" className="data-[state=active]:bg-rose-500 data-[state=active]:text-white">
              <User className="mr-2 h-4 w-4" />
              Staff Management
            </TabsTrigger>
          </TabsList>

          <TabsContent value="rooms">
            <div className="grid gap-6">
              <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold text-gray-800">Rooms Management</h2>
                <Button className="bg-rose-500 hover:bg-rose-600" onClick={handleAddRoom}>
                  <Plus className="mr-2 h-4 w-4" />
                  Add New Room
                </Button>
              </div>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <div className="grid gap-4 md:grid-cols-3">
                    <div className="space-y-2">
                      <Label htmlFor="filter-room-type">Filter by Type</Label>
                      <Select>
                        <SelectTrigger id="filter-room-type" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="All Types" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="all">All Types</SelectItem>
                          <SelectItem value="standard">Standard</SelectItem>
                          <SelectItem value="deluxe">Deluxe</SelectItem>
                          <SelectItem value="suite">Suite</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="filter-room-status">Filter by Status</Label>
                      <Select>
                        <SelectTrigger id="filter-room-status" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="All Statuses" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="all">All Statuses</SelectItem>
                          <SelectItem value="available">Available</SelectItem>
                          <SelectItem value="occupied">Occupied</SelectItem>
                          <SelectItem value="maintenance">Maintenance</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="search-room">Search</Label>
                      <Input
                        id="search-room"
                        placeholder="Search by room number"
                        className="border-rose-200 focus-visible:ring-rose-500"
                      />
                    </div>
                  </div>
                </CardContent>
              </Card>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Room No.</TableHead>
                        <TableHead>Type</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Price</TableHead>
                        <TableHead>Last Cleaned</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {rooms.map((room) => (
                        <TableRow key={room.id}>
                          <TableCell className="font-medium">{room.roomNumber}</TableCell>
                          <TableCell>{room.roomType}</TableCell>
                          <TableCell>
                            <Badge
                              className={
                                room.status === "Available"
                                  ? "bg-green-500"
                                  : room.status === "Occupied"
                                    ? "bg-blue-500"
                                    : "bg-amber-500"
                              }
                            >
                              {room.status}
                            </Badge>
                          </TableCell>
                          <TableCell>{room.price}</TableCell>
                          <TableCell>{room.lastCleaned}</TableCell>
                          <TableCell>
                            <div className="flex gap-2">
                              <Select onValueChange={(value) => handleUpdateRoomStatus(room.id, value)}>
                                <SelectTrigger className="h-8 w-[130px] border-rose-200">
                                  <SelectValue placeholder="Change Status" />
                                </SelectTrigger>
                                <SelectContent>
                                  <SelectItem value="Available">Available</SelectItem>
                                  <SelectItem value="Occupied">Occupied</SelectItem>
                                  <SelectItem value="Maintenance">Maintenance</SelectItem>
                                </SelectContent>
                              </Select>
                              <Button
                                variant="outline"
                                size="sm"
                                className="border-rose-200 text-rose-600 hover:bg-rose-50"
                              >
                                Edit
                              </Button>
                            </div>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value="bookings">
            <div className="grid gap-6">
              <h2 className="text-2xl font-bold text-gray-800">Bookings Management</h2>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <div className="grid gap-4 md:grid-cols-3">
                    <div className="space-y-2">
                      <Label htmlFor="filter-booking-status">Filter by Status</Label>
                      <Select>
                        <SelectTrigger id="filter-booking-status" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="All Statuses" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="all">All Statuses</SelectItem>
                          <SelectItem value="active">Active</SelectItem>
                          <SelectItem value="completed">Completed</SelectItem>
                          <SelectItem value="cancelled">Cancelled</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="filter-date">Filter by Date</Label>
                      <Input id="filter-date" type="date" className="border-rose-200 focus-visible:ring-rose-500" />
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="search-booking">Search</Label>
                      <Input
                        id="search-booking"
                        placeholder="Search by name or booking ID"
                        className="border-rose-200 focus-visible:ring-rose-500"
                      />
                    </div>
                  </div>
                </CardContent>
              </Card>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Booking ID</TableHead>
                        <TableHead>Customer</TableHead>
                        <TableHead>Room</TableHead>
                        <TableHead>Check-in</TableHead>
                        <TableHead>Check-out</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Amount</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {bookings.map((booking) => (
                        <TableRow key={booking.id}>
                          <TableCell className="font-medium">{booking.id}</TableCell>
                          <TableCell>{booking.customerName}</TableCell>
                          <TableCell>{booking.roomNumber}</TableCell>
                          <TableCell>{booking.checkIn}</TableCell>
                          <TableCell>{booking.checkOut}</TableCell>
                          <TableCell>
                            <Badge
                              className={
                                booking.status === "Active"
                                  ? "bg-blue-500"
                                  : booking.status === "Completed"
                                    ? "bg-green-500"
                                    : "bg-amber-500"
                              }
                            >
                              {booking.status}
                            </Badge>
                          </TableCell>
                          <TableCell>{booking.totalAmount}</TableCell>
                          <TableCell>
                            <div className="flex gap-2">
                              {booking.status === "Active" && (
                                <Button
                                  size="sm"
                                  className="bg-rose-500 hover:bg-rose-600"
                                  onClick={() => handleCheckoutGuest(booking.id)}
                                >
                                  Checkout
                                </Button>
                              )}
                              <Button
                                variant="outline"
                                size="sm"
                                className="border-rose-200 text-rose-600 hover:bg-rose-50"
                              >
                                Details
                              </Button>
                            </div>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </CardContent>
              </Card>
            </div>
          </TabsContent>

          <TabsContent value="staff">
            <div className="grid gap-6">
              <div className="flex justify-between items-center">
                <h2 className="text-2xl font-bold text-gray-800">Staff Management</h2>
                <Button className="bg-rose-500 hover:bg-rose-600">
                  <Plus className="mr-2 h-4 w-4" />
                  Add Staff Member
                </Button>
              </div>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <div className="grid gap-4 md:grid-cols-3">
                    <div className="space-y-2">
                      <Label htmlFor="filter-position">Filter by Position</Label>
                      <Select>
                        <SelectTrigger id="filter-position" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="All Positions" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="all">All Positions</SelectItem>
                          <SelectItem value="receptionist">Receptionist</SelectItem>
                          <SelectItem value="housekeeper">Housekeeper</SelectItem>
                          <SelectItem value="maintenance">Maintenance</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="filter-staff-status">Filter by Status</Label>
                      <Select>
                        <SelectTrigger id="filter-staff-status" className="border-rose-200 focus:ring-rose-500">
                          <SelectValue placeholder="All Statuses" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="all">All Statuses</SelectItem>
                          <SelectItem value="on-duty">On Duty</SelectItem>
                          <SelectItem value="off-duty">Off Duty</SelectItem>
                        </SelectContent>
                      </Select>
                    </div>

                    <div className="space-y-2">
                      <Label htmlFor="search-staff">Search</Label>
                      <Input
                        id="search-staff"
                        placeholder="Search by name"
                        className="border-rose-200 focus-visible:ring-rose-500"
                      />
                    </div>
                  </div>
                </CardContent>
              </Card>

              <Card className="border-rose-200">
                <CardContent className="pt-6">
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Staff ID</TableHead>
                        <TableHead>Name</TableHead>
                        <TableHead>Position</TableHead>
                        <TableHead>Contact</TableHead>
                        <TableHead>Status</TableHead>
                        <TableHead>Actions</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {staff.map((member) => (
                        <TableRow key={member.id}>
                          <TableCell className="font-medium">{member.id}</TableCell>
                          <TableCell>{member.name}</TableCell>
                          <TableCell>{member.position}</TableCell>
                          <TableCell>{member.contact}</TableCell>
                          <TableCell>
                            <Badge className={member.status === "On Duty" ? "bg-green-500" : "bg-gray-500"}>
                              {member.status}
                            </Badge>
                          </TableCell>
                          <TableCell>
                            <div className="flex gap-2">
                              <Button
                                variant="outline"
                                size="sm"
                                className="border-rose-200 text-rose-600 hover:bg-rose-50"
                              >
                                Edit
                              </Button>
                              <Button
                                variant="outline"
                                size="sm"
                                className="border-rose-200 text-rose-600 hover:bg-rose-50"
                              >
                                Schedule
                              </Button>
                            </div>
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </CardContent>
              </Card>
            </div>
          </TabsContent>
        </Tabs>
      </main>
    </div>
  )
}
