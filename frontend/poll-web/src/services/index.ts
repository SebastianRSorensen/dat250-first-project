import { Poll, PollOptionCreate, Polls, User, Users } from "../interfaces";
import { api } from "./api";

export function getUserId() {
  const user = localStorage.getItem("userData");
  if (user) {
    return JSON.parse(user)?.id;
  }
  return null;
}

export function getUserInfo() {
  const user = localStorage.getItem("userData");
  if (user) {
    return JSON.parse(user);
  }
  return null;
}

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
    const response = await api.post<User>("/users", {
      username,
      email,
    });
    localStorage.setItem("userData", JSON.stringify(response.data)); // TODO: temporary solution, should use token
    return response.data;
  } catch (error) {
    console.error("Failed to create user:", error);
    throw error;
  }
}

export async function createPoll(
  question: string,
  options: PollOptionCreate[]
) {
  const userId = getUserId(); // TODO: temporary solution, should use token
  try {
    const response = await api.post<Poll>("/polls", {
      creator: userId,
      question,
      options,
    });
    return response.data;
  } catch (error) {
    console.error("Failed to create poll:", error);
    throw error;
  }
}

export async function getPolls() {
  try {
    const response = await api.get<Polls>("/polls").then((res) => res.data);
    return response;
  } catch (error) {
    console.error("Failed to get polls:", error);
    throw error;
  }
}

export async function castVote(
  pollId: string,
  optionId: string,
  isUpVote: boolean
) {
  const userId = getUserId(); // TODO: temporary solution
  try {
    const response = await api.post<Poll>(`/polls/${pollId}`, {
      userId,
      selectedOption: optionId,
      isUpVote,
    });
    return response.data;
  } catch (error) {
    console.error("Failed to cast vote:", error);
    throw error;
  }
}
