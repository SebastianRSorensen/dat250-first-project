import { createUser } from "../services";
import { useMutation } from "@tanstack/react-query";
import { useState } from "react";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./manual-install/card";
import { Input } from "./manual-install/input";
import { Button } from "./manual-install/button";
import { Label } from "./manual-install/label";

export function CreateUser() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState<string | null>(null);

  const usePostData = useMutation({
    mutationFn: async (data: { username: string; email: string }) =>
      createUser(data.username, data.email),
    onSuccess: (res) => {
      console.log(res);
    },
    onError: (err) => {
      console.log(err);
    },
  });
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!username || !email) {
      setError("Please fill in all fields");
      return;
    }
    if (!email.includes("@")) {
      setError("Please enter a valid email address");
      return;
    }
    // Here you would typically send the data to your backend
    usePostData.mutate({ username, email });
    setError(null);
    // Reset form
    setUsername("");
    setEmail("");
  };

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle>Create User</CardTitle>
      </CardHeader>
      <form onSubmit={handleSubmit}>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="username">Username</Label>
            <Input
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
            />
          </div>
          <div className="space-y-2">
            <Label htmlFor="email">Email</Label>
            <Input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
            />
          </div>
          {error && <p className="text-red-500 text-sm">{error}</p>}
        </CardContent>
        <CardFooter>
          <Button type="submit" className="w-full">
            Create User
          </Button>
        </CardFooter>
      </form>
    </Card>
  );
}
