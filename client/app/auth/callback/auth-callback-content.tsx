"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useState } from "react";

import { useCurrentUser } from "@/hooks/use-auth";
import { Spinner } from "@/components/ui/spinner";

export function AuthCallbackContent() {
  const router = useRouter();
  const params = useSearchParams();
  const token = params.get("token");
  const error = params.get("error");
  const [tokenStored, setTokenStored] = useState(false);
  const { data: user, isLoading, isFetched } = useCurrentUser({ enabled: tokenStored });

  useEffect(() => {
    // Handle error from OAuth
    if (error) {
      router.replace(`/login?error=${error}`);
      return;
    }

    // Extract and store token from URL
    if (token) {
      localStorage.setItem("auth_token", token);
      setTokenStored(true);
      // Clear token from URL for security
      router.replace("/auth/callback");
      return;
    }

    // If no token and already checked, redirect
    if (tokenStored && isFetched) {
      if (user) {
        router.replace("/dashboard");
      } else {
        router.replace("/login?error=unauthorized");
      }
    }
  }, [token, error, tokenStored, isFetched, user, router]);

  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-3">
      <Spinner className="size-6" />
      <p className="text-sm text-muted-foreground">Finishing GitHub sign-in ...</p>
    </div>
  );
}
