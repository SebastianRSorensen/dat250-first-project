import { Poll, PollOptionCreate, Polls, User, Users } from "../interfaces";
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
    const response = await api.post<User>("/users", {
      username,
      email,
    });
    return response.data;
  } catch (error) {
    console.error("Failed to create user:", error);
    throw error;
  }
}

export async function createPoll(
  creator: string,
  question: string,
  options: PollOptionCreate[]
) {
  try {
    const response = await api.post<Poll>("/polls", {
      creator,
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
  userId: string,
  pollId: string,
  optionId: string,
  isUpVote: boolean
) {
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
