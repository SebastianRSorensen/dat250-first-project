import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

/**
 * Merges and applies CSS classes to create a combined class string.
 *
 * @param {...ClassValue} inputs - CSS classes or class expressions to merge.
 * @returns {string} - A string containing the merged class names.
 */
export function cn(...inputs: ClassValue[]): string {
  return twMerge(clsx(inputs));
}
