import { Users } from "../interfaces";
import { api } from "./api";

export async function getUsers() {
  try {
    const response = await api.get<Users>("/users").then((res) => res.data);
    return response;
  } catch (error) {
    console.error("Failed to get users:", error);
    throw error;
  }
}

export async function createUser(username: string, email: string) {
  try {
    const response = await api.post<Users>("/users", {
      username,
      email,
    });
    return response.data;
  } catch (error) {
    console.error("Failed to create user:", error);
    throw error;
  }
}
