import { useEffect, useState } from "react";
import { getUsers } from "../services";
import { Users } from "../interfaces";

export function CreateUser() {
  const [users, setUsers] = useState<Users>([]);
  const [loading, setLoading] = useState(true);
  useEffect(() => {
    const fetchUsers = async () => {
      const fetchedUsers = await getUsers();
      setUsers(fetchedUsers);
      setLoading(false);
    };

    fetchUsers();
  }, []);

  return (
    <div>
      {loading
        ? "Loading..."
        : users?.length > 0
        ? users?.map((user) => (
            <div key={user.username}>
              <div>{user.username}</div>
              <div>{user.email}</div>
              {user.polls.map((poll) => (
                <div key={poll.pollId}>
                  <div>{poll.question}</div>
                  <div>{poll.createdAt}</div>
                  <div>{poll.closesAt}</div>
                  {poll.options.map((option) => (
                    <div key={option.presentationOrder}>
                      <div>{option.caption}</div>
                    </div>
                  ))}
                </div>
              ))}
            </div>
          ))
        : "No users found"}
    </div>
  );
}
