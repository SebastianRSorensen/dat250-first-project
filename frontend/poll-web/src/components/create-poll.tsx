import { useState } from "react";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./manual-install/card";
import { PlusCircle, X } from "lucide-react";
import { Label } from "./manual-install/label";
import { Input } from "./manual-install/input";
import { Button } from "./manual-install/button";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { PollOptionCreate } from "../interfaces";
import { createPoll } from "../services";

export function CreatePoll() {
  const queryClient = useQueryClient();
  const [question, setQuestion] = useState("");
  const [options, setOptions] = useState<PollOptionCreate[]>([
    { presentationOrder: 1, caption: "" },
    { presentationOrder: 2, caption: "" },
  ]);
  const [error, setError] = useState<string | null>(null);

  const handleAddOption = () => {
    const newOption = {
      id: (options.length + 1).toString(), // Simple ID generation, consider using a more robust method in production
      presentationOrder: options.length + 1,
      caption: "",
    };
    setOptions([...options, newOption]);
  };

  const handleRemoveOption = (index: number) => {
    const updatedOptions = options
      .filter((_, i) => i !== index)
      .map((option, idx) => ({
        ...option,
        presentationOrder: idx + 1, // Update the presentation order after removing an item
      }));
    setOptions(updatedOptions);
  };

  const handleOptionChange = (index: number, value: string) => {
    const newOptions = [...options];
    newOptions[index] = { ...newOptions[index], caption: value };
    setOptions(newOptions);
  };

  const useCreatePoll = useMutation({
    mutationFn: async (data: {
      question: string;
      options: PollOptionCreate[];
    }) => createPoll(data.question, data.options),
    onSuccess: (res) => {
      queryClient.invalidateQueries({ queryKey: ["polls"] });
      console.log(res);
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!question || options.some((option) => !option)) {
      setError("Please fill in all fields");
      return;
    }
    if (options.length < 2) {
      setError("Please add at least two options");
      return;
    }
    useCreatePoll.mutate({ question, options });
    console.log("Poll created:", { question, options });
    setError(null);
    // Reset form
    setQuestion("");
    setOptions([
      { presentationOrder: 1, caption: "" },
      { presentationOrder: 2, caption: "" },
    ]);
  };

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle>Create Poll</CardTitle>
      </CardHeader>
      <form onSubmit={handleSubmit}>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="question">Question</Label>
            <Input
              id="question"
              value={question}
              onChange={(e) => setQuestion(e.target.value)}
              placeholder="Enter your poll question"
            />
          </div>
          <div className="space-y-2">
            <Label>Options</Label>
            {options.map((option, index) => (
              <div
                key={`${option.caption}-${index}`}
                className="flex items-center space-x-2"
              >
                <Input
                  value={option.caption}
                  onChange={(e) => handleOptionChange(index, e.target.value)}
                  placeholder={`Option ${index + 1}`}
                />
                {index >= 2 && (
                  <Button
                    type="button"
                    variant="ghost"
                    size="icon"
                    onClick={() => handleRemoveOption(index)}
                  >
                    <X className="h-4 w-4" />
                  </Button>
                )}
              </div>
            ))}

            <Button
              type="button"
              variant="outline"
              size="sm"
              className="mt-2"
              onClick={handleAddOption}
            >
              <PlusCircle className="h-4 w-4 mr-2" /> Add Option
            </Button>
          </div>
          {error && <p className="text-red-500 text-sm">{error}</p>}
        </CardContent>
        <CardFooter>
          <Button type="submit" className="w-full">
            Create Poll
          </Button>
        </CardFooter>
      </form>
    </Card>
  );
}
