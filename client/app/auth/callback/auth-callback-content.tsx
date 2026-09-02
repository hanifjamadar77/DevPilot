"use client";

import { useRouter, useSearchParams } from "next/navigation";
import { useEffect, useRef } from "react";

import { Spinner } from "@/components/ui/spinner";

export function AuthCallbackContent() {
  const router = useRouter();
  const params = useSearchParams();
  const hasProcessed = useRef(false);

  useEffect(() => {
    // Prevent double execution
    if (hasProcessed.current) return;
    hasProcessed.current = true;

    const token = params.get("token");
    const error = params.get("error");

    console.log("[AuthCallback] Processing callback", { 
      hasToken: !!token, 
      hasError: !!error,
      tokenLength: token?.length 
    });

    // Handle error
    if (error) {
      console.error("[AuthCallback] OAuth error:", error);
      router.push(`/login?error=${error}`);
      return;
    }

    // Extract and store token
    if (token) {
      console.log("[AuthCallback] Storing token in localStorage");
      try {
        localStorage.setItem("auth_token", token);
        const stored = localStorage.getItem("auth_token");
        console.log("[AuthCallback] Token stored successfully", { 
          stored: !!stored,
          matches: stored === token 
        });
      } catch (e) {
        console.error("[AuthCallback] Failed to store token:", e);
        router.push("/login?error=storage_failed");
        return;
      }

      // Redirect to dashboard - new page load will pick up token from localStorage
      console.log("[AuthCallback] Redirecting to dashboard");
      router.push("/dashboard");
      return;
    }

    // No token found
    console.error("[AuthCallback] No token or error in URL");
    router.push("/login?error=no_token");
  }, [params, router]);

  return (
    <div className="flex min-h-svh flex-col items-center justify-center gap-3">
      <Spinner className="size-6" />
      <p className="text-sm text-muted-foreground">Finishing GitHub sign-in ...</p>
    </div>
  );
}
